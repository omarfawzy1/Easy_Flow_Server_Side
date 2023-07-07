package com.easy_flow_server.service.passenger_services;

import com.easy_flow_server.dto.Models.ResetPassword;
import com.easy_flow_server.dto.Views.*;
import com.easy_flow_server.entity.*;
import com.easy_flow_server.error.BadRequestException;
import com.easy_flow_server.error.NotFoundException;
import com.easy_flow_server.error.ResponseMessage;
import com.easy_flow_server.repos.*;
import com.easy_flow_server.security.PasswordManager;
import com.easy_flow_server.service.notification.FirebaseNotificationService;
import com.easy_flow_server.service.notification.PassengerNotification;
import com.easy_flow_server.service.password_reset_services.ResetPasswordTokenService;
import com.easy_flow_server.service.payment_services.SubscriptionService;
import com.easy_flow_server.service.payment_services.TripService;
import com.easy_flow_server.service.payment_services.WalletService;
import com.easy_flow_server.dto.Models.UpdatePassword;
import com.easy_flow_server.dto.Models.UpdateProfileModel;
import com.easy_flow_server.service.utils.ImageUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Service
public class PassengerServiceImplementation implements PassengerService {

    private final TripRepo tripRepo;
    private final PassengersRepo passengerRepo;
    private final WalletService walletService;
    private final TransactionRepo transactionRepo;
    private final TripService tripService;
    private final SubscriptionService subscriptionService;
    private final SubscriptionRepo subscriptionRepo;
    private final PlanRepository planRepository;
    private final FirebaseNotificationService firebaseNotificationService;
    private final ResetPasswordTokenService resetPasswordTokenService;
    private final ResetPasswordTokenRepo resetPasswordTokenRepo;
    private final PasswordManager passwordManager;
    private final OwnerRepo ownerRepo;
    private final PasswordEncoder passwordEncoder;

    public PassengerServiceImplementation(WalletService walletService, TripRepo tripRepo, PassengersRepo passengerRepo, PasswordEncoder passwordEncoder, TransactionRepo transactionRepo, TripService tripService, PasswordManager passwordManager, SubscriptionService subscriptionService, SubscriptionRepo subscriptionRepo, PlanRepository planRepository, FirebaseNotificationService firebaseNotificationService, OwnerRepo ownerRepo, ResetPasswordTokenService resetPasswordTokenService, ResetPasswordTokenRepo resetPasswordTokenRepo) {
        this.walletService = walletService;
        this.tripRepo = tripRepo;
        this.passengerRepo = passengerRepo;
        this.passwordEncoder = passwordEncoder;
        this.transactionRepo = transactionRepo;
        this.tripService = tripService;
        this.passwordManager = passwordManager;
        this.subscriptionService = subscriptionService;
        this.subscriptionRepo = subscriptionRepo;
        this.planRepository = planRepository;
        this.firebaseNotificationService = firebaseNotificationService;
        this.ownerRepo = ownerRepo;
        this.resetPasswordTokenService = resetPasswordTokenService;
        this.resetPasswordTokenRepo = resetPasswordTokenRepo;
    }

    public List<TripView> getMytrips(String username) throws BadRequestException {
        if (username == null || username.equalsIgnoreCase("anonymous"))
            throw new BadRequestException("Not Authenticated");

        return tripRepo.findAllByPassengerUsernameAndStatus(username, Status.Closed, TripView.class);
    }

    @Override
    public List<TripView> getMytrips(Date date, String username) throws BadRequestException {
        if (username == null || username.equalsIgnoreCase("anonymous"))
            throw new BadRequestException("Not Authenticated");

        return tripRepo.findAllByPassengerUsernameAndStatusAndStartTimeGreaterThanEqual(username, Status.Closed, date, TripView.class);
    }

    @Override
    public PassagnerDetails getMyProfile() throws BadRequestException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getPrincipal().toString();
        if (username == null || username.equalsIgnoreCase("anonymous")) {
            throw new BadRequestException("Not Authenticated");
        }
        return passengerRepo.findProjectedByUsername(username);
    }

    @Override
    public List<PassagnerBriefDetails> getAllPassangers() {
        return passengerRepo.findAllProjectedBy();
    }

    @Override
    public PassagnerDetails getPassengerDetails(String username) throws NotFoundException {
        PassagnerDetails passenger = passengerRepo.findAllProjectedByUsername(username);
        if (passenger == null)
            throw new NotFoundException("Passenger Not Found");
        return passenger;
    }


    @Override
    public Passenger getPassenger(String username) throws NotFoundException {
        Passenger passenger = passengerRepo.findByUsernameIgnoreCase(username);
        if (passenger == null)
            throw new NotFoundException("Passenger Not Found");
        return passenger;
    }

    @Override
    public ResponseMessage deletePassenger(String username) {
        Passenger passenger = passengerRepo.findByUsernameIgnoreCase(username);
        if (passenger == null)
            return new ResponseMessage("Passenger Not Found", HttpStatus.BAD_REQUEST);
        try {
            for (Trip t : passenger.getTrips())
                t.setPassenger(null);
            for (Privilege p : passenger.getPrivileges())
                p.getPassengers().remove(passenger);

            passengerRepo.deleteByUsernameIgnoreCase(username);
        } catch (Exception e) {
            return new ResponseMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseMessage("Passenger deleted Successfully", HttpStatus.OK);
    }

    @Override
    public ResponseMessage passengerStatus(String username) throws NotFoundException {
        Passenger passenger = passengerRepo.findByUsernameIgnoreCase(username);
        if (passenger == null)
            throw new NotFoundException("Passenger Not Found");
        passenger.setActive(!passenger.isActive());
        passengerRepo.save(passenger);
        return new ResponseMessage("Success", HttpStatus.OK);
    }

    @Override
    public int getAllPassangersCount() {
        return (int) passengerRepo.count();
    }

    @Override
    public int getPassengersCountWithPrivilege(String privilege) {
        return passengerRepo.getPassengersCountWithPrivilege(privilege);
    }

    @Override
    public void rechargePassenger(String username, double amount) {
        Passenger passenger = passengerRepo.findByUsernameIgnoreCase(username);
        Transaction transaction = new Transaction(passenger, amount);
        transactionRepo.save(transaction);
        walletService.recharge(passenger.getWallet().getId(), amount);
        PassengerNotification passengerNotification = new PassengerNotification(
                String.format("%f EGP have been successfully added to your wallet", amount),
                "Successful Recharge");
        firebaseNotificationService.notifyPassenger(username, passengerNotification);
    }


    @Override
    public List<TripId> getOpenTrips(int numberOfTickets, String passengerUsername) throws NotFoundException {
        return tripService.getOpenTrips(numberOfTickets, passengerUsername);
    }


    @Override
    public ResponseMessage makeSubscription(String ownerName, String planName) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getPrincipal().toString();
        Passenger passenger = passengerRepo.findByUsernameIgnoreCase(username);
        if (passenger == null) {
            return new ResponseMessage("Passenger not found", HttpStatus.NOT_FOUND);
        }
        Plan plan = planRepository.findByNameAndOwnerName(planName, ownerName, Plan.class);
        if (plan == null) {
            return new ResponseMessage("The plan not exist", HttpStatus.NOT_FOUND);
        }

        return subscriptionService.makeSubscription(passenger, plan);
    }

    @Override
    public List<SubscriptionView> getMySubscriptions() throws NotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getPrincipal().toString();
        if (!passengerRepo.existsByUsernameIgnoreCase(username)) {
            throw new NotFoundException("Sorry, The passenger Not found");
        }

        return subscriptionRepo.findAllByPassengerUsername(username, SubscriptionView.class);

    }


    @Override
    public ResponseMessage sendResetPasswordToken(String email) {
        Passenger passenger = passengerRepo.findByEmailIgnoreCase(email);
        if (passenger == null) {
            return new ResponseMessage("Incorrect email", HttpStatus.NOT_FOUND);
        }
        return resetPasswordTokenService.sendResetPasswordToken(passenger);
    }

    @Override
    public ResponseMessage resetPassengerPassword(String key, ResetPassword newPassword) {
        ResetPasswordToken resetPasswordToken = resetPasswordTokenRepo.findByToken(key);
        if (resetPasswordToken == null) {
            return new ResponseMessage("Sorry, The token is invalid", HttpStatus.NOT_FOUND);
        }
        Passenger passenger = resetPasswordToken.getPassenger();

        return passwordManager.resetPassword(passenger, newPassword);
    }

    @Override
    public ResponseMessage updateProfile(Principal principal, UpdateProfileModel UpdateProfileModel) {
        String username = principal.getName();
        Passenger passenger = passengerRepo.findByUsernameIgnoreCase(username);
        try {
            passenger.setFirstName(UpdateProfileModel.getFirstName());

            passenger.setLastName(UpdateProfileModel.getLastName());

            passenger.setEmail(UpdateProfileModel.getEmail());

            passenger.setPhoneNumber(UpdateProfileModel.getPhoneNumber());

            passenger.setGender(UpdateProfileModel.getGender());
            passengerRepo.save(passenger);
        } catch (Exception e) {
            return new ResponseMessage("invalid data", HttpStatus.BAD_REQUEST);
        }
        return new ResponseMessage("done", HttpStatus.OK);

    }

    @Override
    public ResponseMessage setPin(Principal principal, String pin) {
        Passenger passenger = passengerRepo.findByUsernameIgnoreCase(principal.getName());
        if (passenger == null) {
            return new ResponseMessage("The passenger not exist", HttpStatus.NOT_FOUND);
        }
        passenger.setPin(pin);
        passengerRepo.save(passenger);
        return new ResponseMessage("Success", HttpStatus.OK);
    }

    @Override
    public ResponseMessage reverseSubscriptionRepurchase(Principal principal, String ownerName, String planName) {
        System.out.println(principal.getName() + " " + ownerName + " " + planName);
        Subscription subscription = subscriptionRepo.findByPassengerUsernameAndPlanOwnerNameAndPlanName(principal.getName(), ownerName, planName);
        if (subscription == null) {
            return new ResponseMessage("Subscription not found", HttpStatus.NOT_FOUND);
        }
        subscription.setRepurchase(!subscription.isRepurchase());
        subscriptionRepo.save(subscription);
        return new ResponseMessage("Success", HttpStatus.OK);
    }

    @Override
    public byte[] getOwnerImage(String ownerName) throws NotFoundException {
        Owner owner = ownerRepo.findByName(ownerName);
        if (owner == null) {
            throw new NotFoundException("The owner not found");
        }
        ImageData ownerImageData = owner.getImageData();
        if (ownerImageData == null) return null;
        return ImageUtil.decompressImage(ownerImageData.getImageData());
    }

    @Override
    public ResponseMessage updatePassword(String name, UpdatePassword updatePassword) {
        User user = passengerRepo.findByUsernameIgnoreCase(name);
        if (user == null) {
            return new ResponseMessage("Passenger Not found", HttpStatus.NOT_FOUND);
        }
        if (!passwordEncoder.matches(updatePassword.getOldPassword(), user.getPassword())) {
            return new ResponseMessage("The old password is incorrect", HttpStatus.BAD_REQUEST);
        }
        return passwordManager.resetPassword(user, updatePassword.getResetPassword());
    }

    @Override
    public ResponseMessage renewSubscription(String username, String ownerName, String planName) {
        Passenger passenger = passengerRepo.findByUsernameIgnoreCase(username);
        if (passenger == null) {
            return new ResponseMessage("Passenger not found", HttpStatus.NOT_FOUND);
        }
        Plan plan = planRepository.findByNameAndOwnerName(planName, ownerName, Plan.class);
        if (plan == null) {
            return new ResponseMessage("The plan not exist", HttpStatus.NOT_FOUND);
        }
        return subscriptionService.renewSubscription(passenger, plan);
    }

}
