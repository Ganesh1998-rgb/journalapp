package com.ganesh.journalApp.service;

import com.ganesh.journalApp.dto.JournalEntryRequestDTO;
import com.ganesh.journalApp.dto.JournalEntryResponseDTO;
import com.ganesh.journalApp.entity.JournalEntry;
import com.ganesh.journalApp.entity.User;
import com.ganesh.journalApp.repository.JournalRepository;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class JournalEntryService {
private final ModelMapper modelMapper;
    private final  JournalRepository journalRepository;
    private final UserService userService;

    @Transactional
    public JournalEntryResponseDTO saveEntry(JournalEntryRequestDTO dto){
        try {
            JournalEntry entry = modelMapper.map(dto, JournalEntry.class);
entry.setCreatedAt(LocalDateTime.now());
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userService.findByUserName(username);
            JournalEntry save = journalRepository.save(entry);
            user.getJournalEntries().add(save);
            userService.saveUser(user);
            JournalEntryResponseDTO map = modelMapper.map(save, JournalEntryResponseDTO.class);
map.setId(save.getId().toHexString());
return map;
        } catch (Exception e) {
           throw new RuntimeException("Exception occured "+e);

        }

    }
    
   /* public JournalEntry saveEntry(JournalEntry entry){
      return   journalRepository.save(entry);
    }*/

    public List<JournalEntry> getAll(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User byUserName = userService.findByUserName(username);
        return byUserName.getJournalEntries();
    }

    public JournalEntry getEntryById(ObjectId id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userService.findByUserName(username);

        JournalEntry entry = journalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entry not found"));

        boolean belongsToUser = user.getJournalEntries()
                .stream()
                .anyMatch(x -> x.getId().equals(id));

        if (!belongsToUser) {
            throw new RuntimeException("Unauthorized access to this entry");
        }

        return entry;
    }

    @Transactional
    public void deleteEntry(ObjectId id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userService.findByUserName(username);

        boolean removed = user.getJournalEntries()
                .removeIf(entry -> entry.getId().equals(id));

        if (!removed) {
            throw new RuntimeException("Journal entry not found or not owned by user");
        }
        journalRepository.deleteById(id);
        user.setUserName(null);
        userService.saveUser(user);

    }

    public JournalEntry updateEntry(ObjectId id,JournalEntry entry,String userName){

       /* JournalEntry journalEntry = journalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entry not found"));
        if(entry.getTitle()!=null){
            journalEntry.setTitle(entry.getTitle());
        }

        if(entry.getContent()!=null){
            journalEntry.setContent(entry.getContent());
        }

       return journalRepository.save(journalEntry);*/
return null;

    }

}
