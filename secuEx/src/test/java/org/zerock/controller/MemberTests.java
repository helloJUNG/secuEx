package org.zerock.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.MemberVO;
import org.zerock.mapper.MemberMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/security-context.xml" })
public class MemberTests {

	@Setter(onMethod_ = @Autowired)
	private PasswordEncoder pwEncoder;

	@Setter(onMethod_ = @Autowired)
	private DataSource ds;
	
	@Setter(onMethod_=@Autowired)
	private MemberMapper mapper;
	
	
	@Test
	public void getMember() {
		MemberVO vo = new MemberVO();
		mapper.getMember("user00");
	}
	
	@Test
	public void testTime() {
		
		log.info(mapper.getTime());	
		}
	
	@Test
	public void testInsertAuth() {
		
		String sql = "insert into tbl_member_auth (userid,auth) values(?,?)";
		
		for(int i =0 ; i<100; i++) {
			
			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(sql);
				
				
				if (i>=90) {
				
				pstmt.setString(1, "user" + i);
				pstmt.setString(2, "ROLE_ADMIN");	
				
			}
				

				log.info(pstmt.executeUpdate());

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (con != null) {
						try {
							con.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				}
			} // end finally
			
		}
		
	}

	@Test
	public void testInsertMember() {

		log.info(ds);

		String sql = "insert into tbl_member(userid,userpw,username) values(?,?,?)";

		for (int i = 99; i < 100; i++) {

			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, "user" + i);
				pstmt.setString(2, pwEncoder.encode("pw" + i));
				pstmt.setString(3, "사용자" + i);

				log.info(pstmt.executeUpdate());

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (con != null) {
						try {
							con.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				}
			} // end finally
		}

	}

	@Test
	public void test1() {
		log.info(pwEncoder);

		String enPw = pwEncoder.encode("pw99");

		log.info(enPw);
		
		String target = "$2a$10$a5yS1eT/4EmZbzpgRAgm1.hM9L52Z1/dzmMSqr9SAitBTM7me.JYK";
		
		log.info(pwEncoder.matches("pw99", target));

	}

}
