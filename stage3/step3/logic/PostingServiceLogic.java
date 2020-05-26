package javastory.club.stage3.step3.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javastory.club.stage3.step1.entity.board.Posting;
import javastory.club.stage3.step1.entity.board.SocialBoard;
import javastory.club.stage3.step1.entity.club.TravelClub;
import javastory.club.stage3.step3.logic.storage.MapStorage;
import javastory.club.stage3.step3.service.PostingService;
import javastory.club.stage3.step3.service.dto.PostingDto;
import javastory.club.stage3.step3.util.NoSuchBoardException;
import javastory.club.stage3.step3.util.NoSuchMemberException;
import javastory.club.stage3.step3.util.NoSuchPostingException;
import javastory.club.stage3.util.StringUtil;


public class PostingServiceLogic implements PostingService {
	//
	private Map<String, SocialBoard> boardMap;
	private Map<String, Posting> postingMap;
	private Map<String, TravelClub> clubMap;

	public PostingServiceLogic() {
		//
		this.boardMap = MapStorage.getInstance().getBoardMap();
		this.postingMap = MapStorage.getInstance().getPostingMap();
		this.clubMap = MapStorage.getInstance().getClubMap();
	}

	@Override
	public String register(String boardId, PostingDto postingDto) {
		//
//		SocialBoard foundBoard = boardMap.get(boardId);
//		if (foundBoard == null) {
//			throw new NoSuchBoardException("No such board with id --> " + boardId);
//		}
//
//		Posting newPosting = postingDto.toPostingIn(foundBoard);

		Optional.ofNullable(clubMap.get(boardId)).
				map(club->club.getMembershipBy(postingDto.getWriterEmail()))
				.orElseThrow(()->new NoSuchMemberException("In the club, No such member with admin's email -->" + postingDto.getWriterEmail()));
		Posting newPosting = Optional.ofNullable(boardMap.get(boardId))
				.map(foundBoard->postingDto.toPostingIn(foundBoard))
				.orElseThrow(()->new NoSuchBoardException("No such board with id --> " + boardId));

		postingMap.put(newPosting.getId(), newPosting);
		return newPosting.getId();
	}

	@Override
	public PostingDto find(String postingId) {
		//
//		Posting foundPosting = postingMap.get(postingId);
//		if (foundPosting == null) {
//			throw new NoSuchPostingException("No such a posting with id : " + postingId);
//		}
//		return new PostingDto(foundPosting);

		return Optional.ofNullable(postingMap.get(postingId))
				.map(foundPosting->new PostingDto(foundPosting))
				.orElseThrow(()->new NoSuchPostingException("No such a posting with id : " + postingId));
	}

	@Override
	public List<PostingDto> findByBoardId(String boardId) {
		//
		SocialBoard foundBoard = boardMap.get(boardId);
		if (foundBoard == null) {
			throw new NoSuchBoardException("No such board with id --> " + boardId);
		}
		Optional.ofNullable(boardMap.get(boardId)).orElseThrow(()->new NoSuchBoardException("No such board with id --> " + boardId));
//
//		List<PostingDto> postingDtos = new ArrayList<>();
//		for (Posting posting : postingMap.values()) {
//			if (posting.getBoardId().equals(boardId)) {
//				postingDtos.add(new PostingDto(posting));
//			}
//		}
//		return postingDtos;

		return postingMap.values().stream().filter(posting -> posting.getBoardId().equals(boardId)).map(targetPosting->new PostingDto(targetPosting)).collect(Collectors.toList());
	}

	@Override
	public void modify(PostingDto newPosting) {
		//
		String postingId = newPosting.getUsid();
//		Posting targetPosting = postingMap.get(postingId);
//		if (targetPosting == null) {
//			throw new NoSuchPostingException("No such a posting with id : " + postingId);
//		}

		Posting targetPosting = Optional.ofNullable(postingMap.get(postingId)).orElseThrow(()->new NoSuchMemberException("No such a posting with id : " + postingId));

		// modify if only user inputs some value.
		if (StringUtil.isEmpty(newPosting.getTitle())) {
			newPosting.setTitle(targetPosting.getTitle());
		}
		if (StringUtil.isEmpty(newPosting.getContents())) {
			newPosting.setContents(targetPosting.getContents());
		}
		
		postingMap.put(postingId, newPosting.toPostingIn(newPosting.getUsid(),targetPosting.getBoardId()));
	}

	@Override
	public void remove(String postingId) {
		//
//		if (postingMap.get(postingId) == null) {
//			throw new NoSuchPostingException("No such a posting with id : " + postingId);
//		}
//		postingMap.remove(postingId);

		Optional.ofNullable(postingMap.get(postingId)).orElseThrow(()->new NoSuchMemberException("No such a posting with id : " + postingId));

		postingMap.remove(postingId);
	}

	@Override
	public void removeAllIn(String boardId) {
		//
//		for(Posting posting : postingMap.values()) {
//			if (posting.getBoardId().equals(boardId)) {
//				//
//				postingMap.remove(posting.getId());
//			}
//		}
		postingMap.values()
				.stream()
				.forEach(posting -> postingMap.remove(posting.getId()));
	}
}
