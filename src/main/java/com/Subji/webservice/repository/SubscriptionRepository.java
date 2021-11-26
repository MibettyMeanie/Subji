package com.Subji.webservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Subji.webservice.entities.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

}
