
package services;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.UserAccount;
import utilities.AbstractTest;
import domain.Application;
import domain.Box;
import domain.Curriculum;
import domain.Endorsement;
import domain.HandyWorker;
import domain.Note;
import domain.Phase;
import domain.SocialProfile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class PhaseServiceTest extends AbstractTest {

	//Service----------------------------------------------
	@Autowired
	private PhaseService		phaseService;
	@Autowired
	private HandyWorkerService	handyWorkerService;


	//Test----------------------------------------------

	@Test
	public void testCreate() {
		System.out.println("----testCreate()----");

		try {
			final Phase phase = this.phaseService.create();
			final Phase saved;
			phase.setTitle("tituloPhase");
			phase.setDescription("Phase description");
			phase.setStartMoment(new Date());
			phase.setEndMoment(new Date());
			phase.setNumber(21);

			saved = this.phaseService.save(phase);
			Assert.isTrue(this.phaseService.findAll().contains(saved));
			//Falta m�todo findAll.
			System.out.println("�xito");
		} catch (final Exception e) {
			System.out.println("Fallo, " + e.getMessage());
		}
	}

	@Test
	public void testFindByHandyWorker() {
		final Phase phase = this.phaseService.create();
		phase.setTitle("tituloPhase");
		phase.setDescription("Phase description");
		phase.setStartMoment(new Date());
		phase.setEndMoment(new Date());
		phase.setNumber(21);

		final HandyWorker hw = this.handyWorkerService.create();
		//Actor
		hw.setName("Manolo");
		hw.setEmail("manoloramirez@gmail.com");
		hw.setPhoneNumber("635743987");
		hw.setAddress("C/Lorenzo Marquez");
		hw.setBan(false);
		hw.setMiddleName("Federico");
		hw.setSurname("Gomez");
		hw.setPhotoURL("http://www.manolophtoso.com");
		hw.setSocialProfiles(Arrays.asList(new SocialProfile()));
		hw.setUserAccount(new UserAccount());
		hw.setBoxes(Arrays.asList(new Box()));
		//Endorser
		hw.setEndorsements(Arrays.asList(new Endorsement()));
		hw.setScore(65);
		//HandyWorker
		hw.setMake("Muebles Manolo");
		hw.setApplications(Arrays.asList(new Application()));
		hw.setPlannedPhases(Arrays.asList(new Phase()));
		hw.setNotes(Arrays.asList(new Note()));
		hw.setCurriculum(new Curriculum());

		final Collection<Phase> otherph = this.phaseService.findByHandyWorker();
	}

	@Test
	public void testDelete() {
		final Phase phase = this.phaseService.create();
		phase.setTitle("tituloPhase");
		phase.setDescription("Phase description");
		phase.setStartMoment(new Date());
		phase.setEndMoment(new Date());
		phase.setNumber(21);

		final Phase anto = this.phaseService.save(phase);
		this.phaseService.delete(anto);
		Assert.isNull(this.phaseService.findOne(anto));
	}
}
