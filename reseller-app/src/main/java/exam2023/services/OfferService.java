package exam2023.services;

import exam2023.constants.Messages;
import exam2023.exceptions.BadRequestException;
import exam2023.models.dtos.offer.OfferDTO;
import exam2023.models.entities.Condition;
import exam2023.models.entities.Offer;
import exam2023.models.entities.User;
import exam2023.repositories.OfferRepository;
import exam2023.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfferService {
    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final ConditionService conditionService;
    private final UserService userService;

    @Autowired
    public OfferService(OfferRepository offerRepository, UserRepository userRepository, ConditionService conditionService, UserService userService) {
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
        this.conditionService = conditionService;
        this.userService = userService;
    }

    public void createOffer(OfferDTO offerDTO, Long userId) {
        Offer offer = this.offerDTOtoOffer(offerDTO, userId);
        this.offerRepository.save(offer);
    }

    @Transactional
    public void buyOffer(Long offerId, Long buyerId) {
        Offer offer = offerRepository.findById(offerId).orElseThrow(() -> new BadRequestException("No such offer!"));
        User buyer = userRepository.findById(buyerId).orElseThrow(() -> new BadRequestException("No such user!"));

        // Remove the offer from the seller's offers collection
        User seller = offer.getCreator();
        seller.getOffers().remove(offer);
        userRepository.save(seller);
        offer.setCreator(null);
        offerRepository.save(offer);

        // Add the offer to the buyer's boughtOffers collection
        buyer.getBoughtOffers().add(offer);
        userRepository.save(buyer);

    }

    private Offer offerDTOtoOffer(OfferDTO offerDTO, Long userId) {

        Offer offer = new Offer();
        offer.setDescription(offerDTO.getDescription());
        offer.setPrice(offerDTO.getPrice());

        Condition condition = this.conditionService.getConditionByName(offerDTO.getCondition());
        if (condition == null){
            throw new BadRequestException(Messages.INVALID_CONDITION);
        }
        offer.setCondition(condition);

        User user = this.userService.getUserById(userId);

        if (user == null){
            throw new BadRequestException(Messages.USER_NOT_FOUND);
        }
        offer.setCreator(user);

        return offer;
    }

    public List<Offer> getUnpurchasedOffersFromOtherUsers(Long userId) {
        User user = this.userService.getUserById(userId);
        return offerRepository.findAllByCreatorNot(user);
    }

    @Transactional
    public void deleteOffer(Long offerId) {
        // Retrieve the offer from the database
        Optional<Offer> optionalOffer = offerRepository.findById(offerId);
        if (optionalOffer.isPresent()) {
            Offer offer = optionalOffer.get();
            // Remove the offer from the creator's offers collection
            offer.getCreator().getOffers().remove(offer);

            // Remove the offer from the boughtOffers collection of all users who bought it
            List<User> users = userRepository.findAll();
            for (User user : users) {
                user.getBoughtOffers().remove(offer);
            }

            // Delete the offer from the database
            offerRepository.delete(offer);
        } else {
            throw new RuntimeException("Offer not found");
        }
    }
}
