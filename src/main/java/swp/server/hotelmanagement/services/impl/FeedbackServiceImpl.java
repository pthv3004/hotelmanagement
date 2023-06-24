package swp.server.hotelmanagement.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import swp.server.hotelmanagement.dtos.*;
import swp.server.hotelmanagement.entities.AccountEntity;
import swp.server.hotelmanagement.entities.FeedbackEntity;
import swp.server.hotelmanagement.entities.RoomEntity;
import swp.server.hotelmanagement.repositories.AccountRepository;
import swp.server.hotelmanagement.repositories.FeedbackRepository;
import swp.server.hotelmanagement.repositories.RoomRepository;
import swp.server.hotelmanagement.services.FeedbackService;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final AccountRepository accountRepository;
    private final RoomRepository roomRepository;

    @Override
    public FeedbackRequest createNewFeedback(FeedbackRequest feedbackRequest) {
        try {
            AccountEntity accountEntity = accountRepository.getOne(feedbackRequest.getAccountId());
            FeedbackEntity feedbackEntity = new FeedbackEntity();
            feedbackEntity.setAccountEntity(accountEntity);
            feedbackEntity.setComment(feedbackRequest.getComment());
            feedbackEntity.setRating(feedbackRequest.getRating());
            feedbackEntity.setRoomEntity(roomRepository.getOne(feedbackRequest.getRoomId()));
            feedbackRepository.save(feedbackEntity);
            return feedbackRequest;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    @Override
    public List<FeedbackResponse> getAllFeedback() {
        try {
            List<FeedbackResponse> feedbackResponses = new ArrayList<>();
            List<FeedbackEntity> feedbackEntities = feedbackRepository.findAll();
            feedbackEntities.stream().forEach(feedbackEntity -> {
                RoomDTO roomDTO = mapRoomEntityToDTO(feedbackEntity.getRoomEntity());
                FeedbackResponse feedbackResponse = new FeedbackResponse(roomDTO,
                        feedbackEntity.getAccountEntity().getProfileEntity().getFirstName(),
                        feedbackEntity.getComment(),
                        feedbackEntity.getRating());
                feedbackResponses.add(feedbackResponse);
            });
            return feedbackResponses;
        } catch (Exception e) {
            e.getMessage();
            return new ArrayList<>();
        }
    }

    @Override
    public List<FeedbackResponse> getFeedbackByRoom(int roomId) {

        try {
            List<FeedbackResponse> feedbackResponses = new ArrayList<>();
            List<FeedbackEntity> feedbackEntities = feedbackRepository.getByRoomEntity(roomRepository.getOne(roomId));
            feedbackEntities.stream().forEach(feedbackEntity -> {
                RoomDTO roomDTO = mapRoomEntityToDTO(feedbackEntity.getRoomEntity());
                FeedbackResponse feedbackResponse = new FeedbackResponse(roomDTO,
                        feedbackEntity.getAccountEntity().getProfileEntity().getFirstName(),
                        feedbackEntity.getComment(),
                        feedbackEntity.getRating());
                feedbackResponses.add(feedbackResponse);
            });
            return feedbackResponses;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    @Override
    public List<FeedbackResponse> getFeedbackByAccount(int accountId) {
        try {
            List<FeedbackResponse> feedbackResponses = new ArrayList<>();
            List<FeedbackEntity> feedbackEntities = feedbackRepository.getByAccountEntity(accountRepository.getOne(accountId));
            feedbackEntities.stream().forEach(feedbackEntity -> {
                RoomDTO roomDTO = mapRoomEntityToDTO(feedbackEntity.getRoomEntity());
                FeedbackResponse feedbackResponse = new FeedbackResponse(roomDTO,
                        feedbackEntity.getAccountEntity().getProfileEntity().getFirstName(),
                        feedbackEntity.getComment(),
                        feedbackEntity.getRating());
                feedbackResponses.add(feedbackResponse);
            });
            return feedbackResponses;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    @Override
    public FeedbackRequest updateFeedback(FeedbackRequest feedbackRequest) {
        try {
            FeedbackEntity oldFeedback = feedbackRepository.getOne(feedbackRequest.getFeedbackId());
            oldFeedback.setRating(feedbackRequest.getRating());
            oldFeedback.setComment(feedbackRequest.getComment());
            feedbackRepository.save(oldFeedback);
            return feedbackRequest;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    @Override
    public FeedbackResponse getFeedbackById(int feedbackId) {
        try {
            FeedbackEntity feedbackEntity = feedbackRepository.getOne(feedbackId);
            FeedbackResponse feedbackResponse = new FeedbackResponse();
            feedbackResponse.setRoomDTO(mapRoomEntityToDTO(feedbackEntity.getRoomEntity()));
            feedbackResponse.setComment(feedbackEntity.getComment());
            feedbackResponse.setRating(feedbackEntity.getRating());
            feedbackResponse.setAccountName(feedbackEntity.getAccountEntity().getProfileEntity().getFirstName());
            return feedbackResponse;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }

    }

    private RoomDTO mapRoomEntityToDTO(RoomEntity roomEntity) {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setImage(roomEntity.getImage());
        roomDTO.setName(roomEntity.getName());
        roomDTO.setPrice(roomEntity.getPrice());
        roomDTO.setRoomCategoryDTO(new RoomCategoryDTO(roomEntity.getRoomcategoryEntity().getId(),
                roomEntity.getRoomcategoryEntity().getName(),
                roomEntity.getRoomcategoryEntity().getAmount(),
                roomEntity.getRoomcategoryEntity().getDescription()));
        roomDTO.setRent(roomEntity.isRent());
        return roomDTO;
    }
}
