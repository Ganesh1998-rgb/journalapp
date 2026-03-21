package com.ganesh.journalapp.repository;

import com.ganesh.journalapp.entity.ConfigJournalAppEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ConfigruationRepository extends MongoRepository<ConfigJournalAppEntity, ObjectId> {

}
