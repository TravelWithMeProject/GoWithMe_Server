//package team.backend.goWithMe.domain.trip.entity;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//import team.backend.goWithMe.domain.trip.repository.TripRepository;
//import team.backend.goWithMe.domain.trip.vo.TripArrivalCount;
//import team.backend.goWithMe.domain.trip.vo.TripArrivalImg;
//import team.backend.goWithMe.domain.trip.vo.TripArrivalName;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class TripTest {
//    private Trip trip;
//    final String OLD_IMG = "trip1.jpg";
//    final String OLD_NAME = "뉴욕";
//
//    @Autowired
//    private TripRepository tripRepository;
//
//
//    @BeforeEach
//    public void initTrip() {
//        TripArrivalName name = TripArrivalName.from(OLD_NAME);
//        TripArrivalCount count = TripArrivalCount.newArrivalCount();
//        TripArrivalImg img = TripArrivalImg.from(OLD_IMG);
//        this.trip = Trip.createTrip(name, count, img);
//    }
//
//    @Test
//    @DisplayName("여행 조회수 증가 테스트")
//    public void increaseArrivalTest() throws Exception {
//        // given
//        TripArrivalCount arrivalCount = trip.getArrivalCount();
//        Long oldCount = arrivalCount.arrivalCount();
//
//        // when
//        trip.increaseArrivalCount();
//
//        // then
//        Long newCount = arrivalCount.arrivalCount();
//
//        assertNotEquals(oldCount, newCount);
//        assertEquals(oldCount, 0);
//        assertEquals(newCount, 1);
//    }
//
//    @Test
//    @DisplayName("여행지 조회수 초기화 테스트")
//    public void resetArrivalCountTest() throws Exception {
//        // given
//        final long ITERATION_COUNT = 5L;
//        for (int i = 0; i < ITERATION_COUNT; i++) {
//            trip.increaseArrivalCount();
//        }
//        TripArrivalCount increasedArrivalCount = trip.getArrivalCount();
//        Long increasedCount = increasedArrivalCount.arrivalCount();
//
//        // when
//        trip.resetArrivalCount();
//
//        // then
//        TripArrivalCount resetArrivalCount = trip.getArrivalCount();
//        Long resetCount = resetArrivalCount.arrivalCount();
//
//        assertNotEquals(increasedCount, resetCount);
//        assertEquals(increasedCount, ITERATION_COUNT);
//        assertEquals(resetCount, 0L);
//    }
//
//    @Test
//    @DisplayName("여행지명 변경 테스트")
//    public void changeArrivalNameTest() throws Exception {
//        // given
//        String oldName = trip.getArrivalName().arrivalName();
//
//        // when
//        trip.changeArrivalName(TripArrivalName.from("서울"));
//
//        // then
//        String newName = trip.getArrivalName().arrivalName();
//        assertNotEquals(oldName, newName);
//        assertEquals(oldName, "뉴욕");
//        assertEquals(newName, "서울");
//    }
//
//    @Test
//    @DisplayName("여행지 이미지 변경 테스트")
//    public void changeArrivalImgTest() throws Exception {
//        final String NEW_IMG = "img2.png";
//        // given
//        String oldImg = trip.getArrivalImg().arrivalImg();
//        // when
//        trip.changeArrivalImg(TripArrivalImg.from(NEW_IMG));
//
//        // then
//        String newImg = trip.getArrivalImg().arrivalImg();
//
//        assertNotEquals(oldImg, newImg);
//        assertEquals(oldImg, OLD_IMG);
//        assertEquals(newImg, NEW_IMG);
//    }
//
//    @Test
//    @DisplayName("lombok @NonNull 어노테이션 동작 테스트")
//    public void test1() throws Exception {
//        TripArrivalName name = TripArrivalName.from(OLD_NAME);
//        TripArrivalCount count = TripArrivalCount.newArrivalCount();
//        TripArrivalImg img = TripArrivalImg.from(OLD_IMG);
//
//        // when, then
//        assertThrows(NullPointerException.class, () -> Trip.createTrip(name, null, null));
//        assertThrows(NullPointerException.class, () -> Trip.createTrip(null, count, null));
//        assertThrows(NullPointerException.class, () -> Trip.createTrip(null, null, img));
//        assertThrows(NullPointerException.class, () -> Trip.createTrip(name, count, null));
//        assertThrows(NullPointerException.class, () -> Trip.createTrip(null, count, img));
//        assertThrows(NullPointerException.class, () -> Trip.createTrip(name, null, img));
//        assertThrows(NullPointerException.class, () -> Trip.createTrip(null, null, null));
//        assertDoesNotThrow(() -> Trip.createTrip(name, count, img));
//    }
//
//    @Test
//    @DisplayName("Trip 엔티티 save 테스트")
//    public void tripEntitySaveTest() throws Exception {
//        // given
//        TripArrivalName name = TripArrivalName.from("뉴욕");
//        TripArrivalCount count = TripArrivalCount.newArrivalCount();
//        TripArrivalImg img = TripArrivalImg.from(OLD_IMG);
//        Trip trip = Trip.createTrip(name, count, img);
//
//        // when
//        Trip savedTrip = tripRepository.save(trip);
//
//        // then
//        assertEquals(name.arrivalName(), savedTrip.getArrivalName().arrivalName());
//        assertEquals(count.arrivalCount(), savedTrip.getArrivalCount().arrivalCount());
//        assertEquals(img.arrivalImg(), savedTrip.getArrivalImg().arrivalImg());
//        assertEquals(1L, savedTrip.getId());
//    }
//
//    @Test
//    @Transactional
//    @Rollback(value = false)
//    @DisplayName("조회수 증가 영속성 테스트")
//    public void addOneTest() throws Exception {
//        // given
//        TripArrivalName name = TripArrivalName.from("뉴욕");
//        TripArrivalCount count = TripArrivalCount.newArrivalCount();
//        TripArrivalImg img = TripArrivalImg.from(OLD_IMG);
//        Trip trip = Trip.createTrip(name, count, img);
//
//        // when
//        Trip savedTrip = tripRepository.save(trip);
//        savedTrip.increaseArrivalCount();
//
//        // then
//        Optional<Trip> foundedTrip = tripRepository.findById(savedTrip.getId());
//        foundedTrip.ifPresent(value -> assertEquals(1L, value.getArrivalCount().arrivalCount()));
//    }
//
//}