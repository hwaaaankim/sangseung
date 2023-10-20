package com.dev.SangSeung02.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dev.SangSeung02.model.Member;
import com.dev.SangSeung02.model.MemberAccount;
import com.dev.SangSeung02.repository.MemberRepository;
import com.dev.SangSeung02.utils.PasswordEncoding;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MemberService implements UserDetailsService {

	@Bean(name = "saveBean")
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

//	@Autowired
//	private PasswordEncoding passwordEncoding;
	
	@Autowired
	private MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("loadUserByUsername");
		Optional<Member> member = memberRepository.findByUsername(username);
		if (!member.isPresent()) {
			throw new UsernameNotFoundException(username);
		}

		MemberAccount mem = new MemberAccount(member.get());

		return mem;
	}

	public String test() {
		PasswordEncoding pe = new PasswordEncoding();
		
		if(pe.matches("12345", "$2a$10$ygz5p72CJN8kJv387Wbw7.XiMRdtlNld.V.L06hiB3To3QLyMvhSW")) {
			return "s";
		}else {
			return "f";
		}
	}
	
	public Member save(Member member) {
		String encodedPassword = passwordEncoder().encode(member.getPassword());
		member.setPassword(encodedPassword);
		member.setEnabled(true);
		member.setContact(false);
		member.setJoindate(new Date());
		member.setRole("ROLE_MEMBER");
		log.info("NEW MEMBER");

//		member.getRoles().add(role);

		return memberRepository.save(member);

	}
	
	public Member insertAdmin(Member member) {
		String encodedPassword = passwordEncoder().encode(member.getPassword());
		member.setPassword(encodedPassword);
		member.setEnabled(true);
		member.setContact(true);
		member.setJoindate(new Date());
		member.setRole("ROLE_ADMIN");
		log.info("NEW MEMBER");

//		member.getRoles().add(role);

		return memberRepository.save(member);

	}

	public void memberUpdate(Member member) {

		Optional<Member> m = memberRepository.findById(member.getId());
		m.ifPresent(me -> {
			me.setBusiness(member.getBusiness());
			me.setName(member.getName());
			me.setPhone(member.getPhone());
			me.setEmail(member.getEmail());

			memberRepository.save(me);
		});

	}

	public String passCheck(String username, String password) {
		Member member = memberRepository.findByUsername(username).get();
		if (!passwordEncoder().matches(password, member.getPassword())) {
			return "fail";
		} else {
			return "success";
		}

	}

	public Member infoChange(Member member, String key) {

		Optional<Member> mem = memberRepository.findByUsername(member.getUsername());

		if (key.equals("PASS")) {
			mem.ifPresent(m -> {
				m.setPassword(passwordEncoder().encode(member.getPassword()));

				memberRepository.save(m);
			});

		} else if (key.equals("INFO")) {
			mem.ifPresent(m -> {
				m.setName(member.getName());
				m.setBusiness(member.getBusiness());
				m.setPhone(member.getPhone());
				m.setEmail(member.getEmail());

				memberRepository.save(m);
			});
		}
		return mem.get();
	}

	public Page<Member> findByDate(Pageable pageable, String startDate, String endDate) throws ParseException {

		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat bf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if ("".equals(startDate) && "".equals(startDate)) {

			Date today = new Date();
			String day = bf.format(today);

			String start = day.substring(0, 10) + " 00:00:00";
			String end = day.substring(0, 10) + " 23:59:00";

			Date first = bf.parse(start);
			Date second = bf.parse(end);
			return memberRepository.findAllByJoindateBetween(pageable, first, second);

		} else if (!"".equals(startDate) && !"".equals(startDate) && startDate.equals(endDate)) {
			String start = startDate.substring(0, 10) + " 00:00:00";
			Date first = f.parse(start);
			Date second = f.parse(start);

			Calendar c = Calendar.getInstance();
			c.setTime(second);
			c.add(Calendar.DATE, 1);
			second = c.getTime();

			return memberRepository.findAllByJoindateBetween(pageable, first, second);

		} else if ("".equals(startDate) && !"".equals(endDate)) {

			Date second = f.parse(endDate);
			return memberRepository.findAllByJoindateLessThan(pageable, second);

		} else if (!"".equals(startDate) && "".equals(endDate)) {
			Date first = f.parse(startDate);
			return memberRepository.findAllByJoindateGreaterThan(pageable, first);
		} else {
			Date first = f.parse(startDate);
			Date second = f.parse(endDate);

			Calendar c = Calendar.getInstance();
			c.setTime(second);
			c.add(Calendar.DATE, 1);
			second = c.getTime();

			return memberRepository.findAllByJoindateBetween(pageable, first, second);
		}
	}
}
