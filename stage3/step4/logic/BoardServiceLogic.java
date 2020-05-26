package javastory.club.stage3.step4.logic;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javastory.club.stage3.step1.entity.board.SocialBoard;
import javastory.club.stage3.step1.entity.club.TravelClub;
import javastory.club.stage3.step4.service.BoardService;
import javastory.club.stage3.step4.service.dto.BoardDto;
import javastory.club.stage3.step4.store.BoardStore;
import javastory.club.stage3.step4.store.ClubStore;
import javastory.club.stage3.step4.util.BoardDuplicationException;
import javastory.club.stage3.step4.util.NoSuchBoardException;
import javastory.club.stage3.step4.util.NoSuchClubException;
import javastory.club.stage3.step4.util.NoSuchMemberException;
import javastory.club.stage3.util.StringUtil;
import javastory.club.stage3.step4.da.map.ClubStoreMapLycler;

public class BoardServiceLogic implements BoardService {
	//
	private BoardStore boardStore;
	private ClubStore clubStore;
	
	public BoardServiceLogic() {
		//
		this.boardStore = ClubStoreMapLycler.getInstance().requestBoardStore();
		this.clubStore = ClubStoreMapLycler.getInstance().requestClubStore();
	}
	
	@Override
	public String register(BoardDto boardDto) {
		//
		String boardId = boardDto.getId();
//		SocialBoard boardFound = boardStore.retrieve(boardId);
//		if (boardFound != null) {
//			throw new BoardDuplicationException("There is board already in the club -->" + boardFound.getName());
//		}
		
		Optional.ofNullable(boardStore.retrieve(boardId)).ifPresent((boardFound)->{throw new BoardDuplicationException("There is board already in the club -->" + boardFound.getName());});

		
//		TravelClub clubFound = clubStore.retrieve(boardId);
//		if (clubFound == null) {
//			throw new NoSuchClubException("No such a club with id: " + boardId);
//		}
		
		TravelClub clubFound = Optional.ofNullable(clubStore.retrieve(boardId)).orElseThrow(()->new NoSuchClubException("No such a club with id: " + boardId));


		
//		if (clubFound.getMembershipBy(boardDto.getAdminEmail()) == null) {
//			throw new NoSuchMemberException("In the club, No such member with admin's email -->" + boardDto.getAdminEmail());
//		}

		
		
		return Optional.ofNullable(clubFound.getMembershipBy(boardDto.getAdminEmail()))
				.map(adminEmail->boardStore.create(boardDto.toBoard()))
				.orElseThrow(()->new NoSuchMemberException("In the club, No such member with admin's email -->" + boardDto.getAdminEmail()));
	}

	@Override
	public BoardDto find(String boardId) {
		//
//		SocialBoard board = boardStore.retrieve(boardId);
//		if (board == null) {
//			throw new NoSuchBoardException("No such board with id --> " + boardId);
//		}
//		
//		return new BoardDto(board);
		
		return Optional.ofNullable(boardStore.retrieve(boardId)).map(board->new BoardDto(board)).orElseThrow(()->new NoSuchBoardException("No such board with id --> " + boardId));
	}
	
	@Override
	public List<BoardDto> findByName(String boardName) {
		//
		List<SocialBoard> boards = boardStore.retrieveByName(boardName);

		if (boards == null || boards.isEmpty()) {
			throw new NoSuchBoardException("No such board with name --> " + boardName);
		}

//		List<BoardDto> boardDtos = new ArrayList<>();
//		for (SocialBoard board : boards) {
//			boardDtos.add(new BoardDto(board));
//		}
//		return boardDtos;
		
		return boards.stream().map(board->new BoardDto(board)).collect(Collectors.toList());
	}

	@Override
	public BoardDto findByClubName(String clubName) {
		//
//		TravelClub club = clubStore.retrieveByName(clubName);
//		if(club == null) {
//			throw new NoSuchClubException("No such a club with name: " + clubName);
//		}
//		
//		SocialBoard board = boardStore.retrieve(club.getId());
//		return new BoardDto(board);
				
		return Optional.ofNullable(clubStore.retrieveByName(clubName))
				.map(club->new BoardDto(boardStore.retrieve(club.getId())))
				.orElseThrow(()->new NoSuchClubException("No such a club with name: " + clubName));
	}

	@Override
	public void modify(BoardDto boardDto) {
		//
//		SocialBoard board = boardStore.retrieve(boardDto.getId());
//		if (board == null) {
//			throw new NoSuchBoardException("No such board with id --> " + boardDto.getId());
//		}
//		
//		boardStore.update(boardDto.toBoard());
		
		SocialBoard targetBoard = Optional.ofNullable(boardStore.retrieve(boardDto.getId()))
				.orElseThrow(()->new NoSuchBoardException("No such board with id --> " + boardDto.getId()));
		
		if(StringUtil.isEmpty(boardDto.getName())) {
			boardDto.setName(targetBoard.getName());
		}
		if(StringUtil.isEmpty(boardDto.getAdminEmail())) {
			
			boardDto.setAdminEmail(targetBoard.getAdminEmail());
		}else {
			Optional.ofNullable(clubStore.retrieve(boardDto.getClubId()))
                    .map(club->club.getMembershipBy(boardDto.getAdminEmail()))
                    .orElseThrow(()->new NoSuchMemberException("In the club, No such member with admin's email -->" + boardDto.getAdminEmail()) );
		}
		
		boardStore.update(boardDto.toBoard());
	}

	@Override
	public void remove(String boardId) {
		//
//		SocialBoard board = boardStore.retrieve(boardId);
//		if (board == null) {
//			throw new NoSuchBoardException("No such board with id --> " + boardId);
//		}
//		
//		boardStore.delete(boardId);
		if(!boardStore.exists(boardId)) {
			throw new NoSuchBoardException("No such board with id --> " + boardId);
		}
		
		
		boardStore.delete(boardId);
	}
}
