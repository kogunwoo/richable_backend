package com.idle.kb_i_dle_backend.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {

//    private final CommunityRepository communityRepository;
//
//    @Override
//    public List<BoardDTO> getAllBoard() {
//        return communityRepository.findAll().stream().map(board -> {
//            BoardDTO boardDTO = new BoardDTO();
//            boardDTO.setId(board.getId());
//            boardDTO.setTitle(board.getTitle());
//            boardDTO.setWriter(board.getWriter());
//            boardDTO.setContents(board.getContents());
//            return boardDTO;
//        }).collect(Collectors.toList());
//    }
}
