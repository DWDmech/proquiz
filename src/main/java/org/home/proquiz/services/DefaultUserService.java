package org.home.proquiz.services;

import java.util.List;
import java.util.Optional;

import org.home.proquiz.entities.User;
import org.home.proquiz.model.UserRepository;
import org.home.proquiz.util.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultUserService implements UserService {
	@Autowired
	private UserRepository uRep;
	
	@Qualifier("encoder")
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
//	@Override
//	@Transactional(readOnly=true)
//	public User getById(Long id) {
//		AssertUtil.isNull(id, this.getClass().getName() + ".getById has error - id is null");
//		AssertUtil.isPositiv(id, 
//				this.getClass().getName() + ".getById has error - id is negative number");
//
//		Optional<User> o = uRep.findById(id);
//		if(o.isPresent())
//			o.get().setPassword(null);
//		return o.isPresent() ? o.get() : null;
//	}
//	
//	@Override
//	@Transactional(readOnly=true)
//	public User getByEmailAndPassword(String email, String password) {
//		AssertUtil.isNull(email, 
//				this.getClass().getName() + ".getByEmailAndPassword has error - email is null");
//		AssertUtil.isNull(password, 
//				this.getClass().getName() + ".getByEmailAndPassword has error - password is null");
//
//		Optional<User> o = uRep.findByEmailAndPassword(email, password);
//		if(o.isPresent())
//			o.get().setPassword(null);
//		return o.isPresent() ? o.get() : null;
//	}
	
	@Override
	public User getByEmail(String email) {
		AssertUtil.isNull(email, 
				this.getClass().getName() + ".getByEmail has error - email is null");
		
		Optional<User> o = uRep.findByEmail(email);
		if(o.isPresent())
			o.get().setPassword(null);
		return o.isPresent() ? o.get() : null;
	}

	@Transactional
	@Override
	public void save(User user) {
		AssertUtil.isNull(user, this.getClass().getName() + ".save has error - user is null");
			String password = passwordEncoder.encode(user.getPassword());
			user.setPassword(password);
			uRep.save(user);
	}

	@Transactional
	@Override
	public void update(User user) {
		AssertUtil.isNull(user, this.getClass().getName() + ".update has error - user is null");
		AssertUtil.isNull(user.getId(), 
				this.getClass().getName() + ".update has error - user.id is null");
		AssertUtil.isPositiv(user.getId(), 
				this.getClass().getName() + ".update has error - user.id is negative number");
		
		if(uRep.existsById(user.getId())) {
			if(user.getPassword() != null)
				user.setPassword(passwordEncoder.encode(user.getPassword()));
			uRep.save(user);
		}
	}

	@Transactional(readOnly=true)
	@Override
	public boolean exist(Long id) {
		AssertUtil.isNull(id, this.getClass().getName() + ".exist has error - id is null");
		AssertUtil.isPositiv(id, 
				this.getClass().getName() + ".exist has error - id is negative number");
		
		return uRep.existsById(id);
	}

	@Transactional(readOnly=true)
	@Override
	public String getUserNameById(Long id) {
		AssertUtil.isNull(id, 
				this.getClass().getName() + ".getUserNameById has error - id is null");
		AssertUtil.isPositiv(id, 
				this.getClass().getName() + ".getUserNameById has error - id is negative number");
		
		if(!uRep.existsById(id)) return null;
		return uRep.findUserNameById(id);
	}

	@Override
	public List<User> usersByAnswer(Long answerId) {
		AssertUtil.isNull(answerId, 
				this.getClass().getName() + ".usersByAnswer has error - answer id is null");
		AssertUtil.isPositiv(answerId, 
				this.getClass().getName() + ".usersByAnswer has error - answer id is negative number");
		
		Optional<List<User>> o = uRep.findByAnswer(answerId);
		
		return o.isPresent() ? o.get() : null;
	}
}
