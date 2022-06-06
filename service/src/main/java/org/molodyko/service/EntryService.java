package org.molodyko.service;

import lombok.RequiredArgsConstructor;
import org.molodyko.dto.EntryDto;
import org.molodyko.entity.Entry;
import org.molodyko.mapper.EntryMapper;
import org.molodyko.repository.EntryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EntryService {

    private final EntryRepository entryRepository;
    private final EntryMapper entryMapper;

    public EntryDto save(EntryDto entryDtoRq) {
        Entry entry = entryMapper.convertDtoToEntity(entryDtoRq);
        entryRepository.saveAndFlush(entry);
        return entryMapper.convertEntityToDto(entry);
    }

    public List<EntryDto> getEntries() {
        List<Entry> entries = entryRepository.findAll();
        return entries.stream()
                .map(entryMapper::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public EntryDto getEntryById(Integer id) {
        return entryRepository.findById(id)
                .map(entry -> entryMapper.convertEntityToDto(entry))
                .orElseThrow();
    }

    public boolean deleteEntryById(Integer id) {
        return entryRepository.findById(id)
                .map(entry -> {
                    entryRepository.delete(entry);
                    return true;
                })
                .orElse(false);
    }
}
