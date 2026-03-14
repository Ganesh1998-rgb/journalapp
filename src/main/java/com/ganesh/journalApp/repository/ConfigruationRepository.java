package com.ganesh.journalApp.repository;

import com.ganesh.journalApp.entity.ConfigJournalAppEntity;
import com.ganesh.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ConfigruationRepository extends MongoRepository<ConfigJournalAppEntity, ObjectId> {

}
