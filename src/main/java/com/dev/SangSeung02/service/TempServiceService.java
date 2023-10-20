package com.dev.SangSeung02.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dev.SangSeung02.model.service.TempService;
import com.dev.SangSeung02.repository.TempServiceRepository;

@Service
public class TempServiceService {

	
	@Autowired
	TempServiceRepository tempServiceRepository;
	
	public Page<TempService> findByDate(
			Pageable pageable, 
			String startDate, 
			String endDate)
			throws ParseException {

		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat bf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if ("".equals(startDate) && "".equals(startDate)) {

			Date today = new Date();
			String day = bf.format(today);

			String start = day.substring(0, 10) + " 00:00:00";
			String end = day.substring(0, 10) + " 23:59:00";

			Date first = bf.parse(start);
			Date second = bf.parse(end);
			return tempServiceRepository.findAllByTodayBetween(pageable, first, second);

		} else if (!"".equals(startDate) && !"".equals(startDate) && startDate.equals(endDate)) {
			String start = startDate.substring(0, 10) + " 00:00:00";
			Date first = f.parse(start);
			Date second = f.parse(start);

			Calendar c = Calendar.getInstance();
			c.setTime(second);
			c.add(Calendar.DATE, 1);
			second = c.getTime();

			return tempServiceRepository.findAllByTodayBetween(pageable, first, second);

		} else if ("".equals(startDate) && !"".equals(endDate)) {

			Date second = f.parse(endDate);
			return tempServiceRepository.findAllByTodayLessThan(pageable, second);

		} else if (!"".equals(startDate) && "".equals(endDate)) {
			Date first = f.parse(startDate);
			return tempServiceRepository.findAllByTodayGreaterThan(pageable, first);
		} else {
			Date first = f.parse(startDate);
			Date second = f.parse(endDate);

			Calendar c = Calendar.getInstance();
			c.setTime(second);
			c.add(Calendar.DATE, 1);
			second = c.getTime();

			return tempServiceRepository.findAllByTodayBetween(pageable, first, second);
		}
	}
}
