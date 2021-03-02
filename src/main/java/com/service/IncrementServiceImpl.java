package com.iamalokit.increment.service;

import java.util.Optional;

import com.repository.IncrementRepository;
import com.service.IncrementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class IncrementServiceImpl implements IncrementService {

	Logger logger = LoggerFactory.getLogger(IncrementServiceImpl.class);


	@Autowired
	IncrementRepository incrementRepository;



	@Override
	public void incrementCounter(Long id) {
		try {
			Optional<Number> optionalNumber = incrementRepository.findById(id);
			if (optionalNumber.isPresent()) {
				Number number = optionalNumber.get();
				int updatedCount = number.getCounter() + 1;
				number.setCounter(updatedCount);
				incrementRepository.save(number);
			}
		} catch (Exception e) {
			logger.error("Exception occurred while incrementing counter", e);
			throw e;
		}

	}

	@Override
	public void setInitialValue(Long id) {
		try {
			boolean isAlreadyExist = incrementRepository.existsById(id);
			if(!isAlreadyExist) {
				Number number = new Number() {
					@Override
					public int intValue() {
						return 0;
					}

					@Override
					public long longValue() {
						return 0;
					}

					@Override
					public float floatValue() {
						return 0;
					}

					@Override
					public double doubleValue() {
						return 0;
					}
				};
				number.setId(id);
				incrementRepository.save(number);
			} else {
				logger.info("Number already exist with the id " + id);
			}
		} catch (Exception e) {
			logger.error("Exception occurred while setting the initial value of number", e);
			throw e;
		}

	}

}
