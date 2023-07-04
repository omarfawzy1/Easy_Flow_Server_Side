package com.example.easy_flow_backend.service.passenger_services;

import com.example.easy_flow_backend.dto.Models.ResetPassword;
import com.example.easy_flow_backend.dto.Models.UpdatePassword;
import com.example.easy_flow_backend.dto.Models.UpdateProfileModel;
import com.example.easy_flow_backend.dto.Views.*;
import com.example.easy_flow_backend.entity.*;
import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.error.ResponseMessage;
import com.example.easy_flow_backend.repos.*;
import com.example.easy_flow_backend.security.PasswordManager;
import com.example.easy_flow_backend.service.notification.FirebaseNotificationService;
import com.example.easy_flow_backend.service.notification.PassengerNotification;
import com.example.easy_flow_backend.service.password_reset_services.ResetPasswordTokenService;
import com.example.easy_flow_backend.service.payment_services.SubscriptionService;
import com.example.easy_flow_backend.service.payment_services.TripService;
import com.example.easy_flow_backend.service.payment_services.WalletService;
import com.google.api.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class PassengerServiceImplementation implements PassengerService {

    @Autowired
    private TripRepo tripRepo;
    @Autowired
    private PassengersRepo passengerRepo;
    @Autowired
    private WalletService walletService;
    @Autowired
    private TransactionRepo transactionRepo;
    @Autowired
    private TripService tripService;
    @Autowired
    private SubscriptionService subscriptionService;
    @Autowired
    private SubscriptionRepo subscriptionRepo;
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private FirebaseNotificationService firebaseNotificationService;
    @Autowired
    private ResetPasswordTokenService resetPasswordTokenService;
    @Autowired
    private ResetPasswordTokenRepo resetPasswordTokenRepo;
    @Autowired
    private PasswordManager passwordManager;
    @Autowired
    private OwnerRepo ownerRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

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
            return new ResponseMessage("Passenger not found", HttpStatus.BAD_REQUEST);
        }
        Plan plan = planRepository.findByNameAndOwnerName(planName, ownerName, Plan.class);
        if (plan == null) {
            return new ResponseMessage("The plan not exist", HttpStatus.BAD_REQUEST);
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
    public ResponseMessage sendResetPasswordToken(String email) throws NotFoundException {
        Passenger passenger = passengerRepo.findByEmailIgnoreCase(email);
        if (passenger == null) {
            throw new NotFoundException("Incorrect email!");
        }
        return resetPasswordTokenService.sendResetPasswordToken(passenger);
    }

    @Override
    public ResponseMessage resetPassengerPassword(String key, ResetPassword newPassword) {
        ResetPasswordToken resetPasswordToken = resetPasswordTokenRepo.findByToken(key);
        if (resetPasswordToken == null) {
            return new ResponseMessage("Sorry, The token is invalid", HttpStatus.BAD_REQUEST);
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
            return new ResponseMessage("The passenger not exist", HttpStatus.BAD_REQUEST);
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
            return new ResponseMessage("Subscription not found", HttpStatus.BAD_REQUEST);
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
        return owner.getImageData().getImageData();
    }

    @Override
    public ResponseMessage updatePassword(String name, UpdatePassword updatePassword) {
        User user = passengerRepo.findByUsernameIgnoreCase(name);
        if (user == null) {
            return new ResponseMessage("Passenger Not found", HttpStatus.BAD_REQUEST);
        }
        if (!passwordEncoder.matches(updatePassword.getOldPassword(), user.getPassword())) {
            return new ResponseMessage("The old password is incorrect", HttpStatus.BAD_REQUEST);
        }
        return passwordManager.resetPassword(user, updatePassword.getResetPassword());
    }

}
