package cc.oobootcamp.principle;

import cc.oobootcamp.principle.parkingLot.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 参与培训的同学需在2月28日之前完成“ParkingLot”6个需求中至少前5个，同时将完成的作业上传至自己的Github账号，并将链接发至敏捷中心张超辉邮zhangchaohui03@zybank.com.cn。
 * 1. 作为一个停车场，能够存车、取车。
 * 2. 作为一个初入职场的停车小弟（Graduate Parking Boy），我能够将车按顺序停放到多个停车场，并可以取出。
 * 3. 作为一个聪明的停车小弟（Smart Parking Boy），我能够将车停在空车位最多的那个停车场。
 * 4. 作为一个超级停车小弟（Super Parking Boy），我能够将车停在空置率最高的那个停车场。
 * 5. 作为停车场的经理（Parking Manager），我要管理多个停车仔，让他们停车，同时也可以自己停车。
 * 6. 作为停车场的主管（Parking Director），我希望看到一张报表，其中包括经理和每个停车仔所管理的车。
 */
public class ParkingLogTest {
    /**
     * given 有1个空车位的停车场
     * when 停1辆车
     * then 返回一张车票
     */
    @Test
    public void should_return_ticket_when_parkingLot_has_one_space_valid() {
        ParkingLot parkingLot = new ParkingLot(1);
        Car car = new Car();
        Ticket ticket = parkingLot.parkingCar(car);
        assertThat(ticket).isNotNull();
    }

    /**
     * given 无停车位的停车场
     * when 停1辆车
     * then 停车失败
     */
    @Test(expected = ParkingLotHasNoEnoughSpaceException.class)
    public void should_return_ParkingLotHasNoEnoughSpaceException_when_has_no_space_valid() {
        ParkingLot parkingLot = new ParkingLot(0);
        Car car = new Car();
        Ticket ticket = parkingLot.parkingCar(car);
    }

    /**
     * given 有自己车的停车场
     * when 取车，给一张停车票，票与车匹配
     * then 取车成功
     */
    @Test
    public void should_return_myCar_when_given_ticket_to_getCar_in_parkingLot_where_has_myCar_valid() {
        ParkingLot parkingLot = new ParkingLot(10);
        Car car = new Car();
        Ticket ticket = parkingLot.parkingCar(car);
//        assertThat(parkingLot.getCar(ticket)).isEqualTo(car);
        assertThat(parkingLot.getCar(ticket)).isSameAs(car);
    }

    /**
     * given 有自己车的停车场
     * when 取车，给一张停车票，票与车不匹配
     * then 取车不成功
     */
    @Test(expected = ParkingLotGetCarFailedException.class)
    public void should_return_ParkingLotGetCarException_when_given_wrong_ticket_to_getCar_in_parkingLot_where_has_myCar_valid() throws ParkingLotHasNoEnoughSpaceException, ParkingLotGetCarFailedException {
        ParkingLot parkingLot = new ParkingLot(10);
        Car car = new Car();
        Ticket rightTicket = parkingLot.parkingCar(car);
        Ticket wrongTicket = new Ticket();
        parkingLot.getCar(wrongTicket);
    }

    /**
     * given 有自己车的停车场
     * when 同一張票取多次車
     * then 取车不成功
     */
    @Test(expected = ParkingLotGetCarFailedException.class)
    public void should_return_ParkingLotGetCarException_when_given_right_ticket_to_getCar_twice_in_parkingLot_where_has_myCar_valid() throws ParkingLotHasNoEnoughSpaceException, ParkingLotGetCarFailedException {
        ParkingLot parkingLot = new ParkingLot(10);
        Car car = new Car();
        Ticket ticket = parkingLot.parkingCar(car);
        parkingLot.getCar(ticket);
        parkingLot.getCar(ticket);
    }

    /**
     * 作为一个初入职场的停车小弟（Graduate Parking Boy），我能够将车按顺序停放到多个停车场，并可以取出
     * given 有三个有车位的停车场
     * when 停车小弟停车三辆
     * then 成功按照顺序停车,并获取三张停车票，每个停车场剩余车位 -1，停车场车辆+1
     */
    @Test
    public void should_return_three_tickets_by_order_of_parkingLot_when_given_three_has_spaces_parkingLot_and_a_graduateParkingBoy_valid() {
        List<ParkingLot> parkingLotList = new ArrayList();
        List<Car> carList = new ArrayList();
        create_three_cars_stop_in_three_parkinglots_scene(parkingLotList, carList);
        ParkingBoy parkingBoy = new GraduateParkingBoy();
        List<Ticket> ticketsList = parkingBoy.toParkingCar(parkingLotList, carList);
        for (int i = 0; i < 3; i++) {
            assertThat(parkingLotList.get(i).getResidueSpace()).isEqualTo(9);
            assertThat(parkingLotList.get(i).getParkedCars().size()).isEqualTo(1);
        }
        assertThat(ticketsList.size()).isEqualTo(3);

    }
    /**
     * 作为一个初入职场的停车小弟（Graduate Parking Boy），我能够将车按顺序停放到多个停车场，并可以取出
     * given 有三个有车位的停车场，停车小弟已经停了按照顺序停了三辆车
     * when 小弟拿票成功取三辆车
     * then 成功取车
     */
    @Test
    public void should_return_three_car_success_by_order_of_parkingLot_when_given_three_has_spaces_parkingLot_and_stoped_car_and_a_graduateParkingBoy_valid() {
        List<ParkingLot> parkingLotList = new ArrayList();
        List<Car> carList = new ArrayList();
        create_three_cars_stop_in_three_parkinglots_scene(parkingLotList, carList);
        ParkingBoy parkingBoy = new GraduateParkingBoy();
        List<Ticket> ticketsList = parkingBoy.toParkingCar(parkingLotList, carList);
        for (int i = 0; i < 3; i++) {
            assertThat(parkingLotList.get(i).getCar(ticketsList.get(i))).isSameAs(carList.get(i));
        }

    }

    /**
     * 作为一个初入职场的停车小弟（Graduate Parking Boy），我能够将车按顺序停放到多个停车场，并可以取出
     * given 有四个停车场，第二个停车场无车位,已经停了三辆车
     * when 小弟拿票取车
     * then 成功取三辆车
     */
    @Test
    public void should_return_three_tickets_success_by_order_of_parkingLot_when_given_four_has_spaces_and_one_full_parkingLot_and_a_graduateParkingBoy_valid() {
        List<ParkingLot> parkingLotList = new ArrayList();
        List<Car> carList = new ArrayList();
        create_three_cars_stop_in_four_parkinglots_and_one_is_full_scene(parkingLotList, carList);
        ParkingBoy parkingBoy = new GraduateParkingBoy();
        List<Ticket> ticketsList = parkingBoy.toParkingCar(parkingLotList, carList);

        List<ParkingLot> hasSpaceParkingLotList = parkingLotList.stream().filter(parkingLot -> parkingLot.getResidueSpace() >0 ).collect(Collectors.toList());
        for (int i = 0; i < carList.size(); i++) {
            int num = i >= 2 ? i + 1 : i;
            assertThat(hasSpaceParkingLotList.get(i).getCar(ticketsList.get(i))).isSameAs(carList.get(i));
        }

    }


    /**
     * 作为一个初入职场的停车小弟（Graduate Parking Boy），我能够将车按顺序停放到多个停车场，并可以取出
     * given 有四个停车场，第三个停车场无车位
     * when 停车小弟停车三辆
     * then 成功按照顺序停车,并获取三张停车票
     */
    @Test
    public void should_return_three_cars_success_by_order_of_parkingLot_when_given_four_has_spaces_and_one_full_parkingLot_and_a_graduateParkingBoy_valid() {
        List<ParkingLot> parkingLotList = new ArrayList();
        List<Car> carList = new ArrayList();
        create_three_cars_stop_in_four_parkinglots_and_one_is_full_scene(parkingLotList, carList);
        ParkingBoy parkingBoy = new GraduateParkingBoy();
        List<Ticket> ticketsList = parkingBoy.toParkingCar(parkingLotList, carList);

        List<ParkingLot> hasSpaceParkingLotList = parkingLotList.stream().filter(parkingLot -> parkingLot.getResidueSpace() >0 ).collect(Collectors.toList());
        for (int i = 0; i < carList.size(); i++) {
            int num = i >= 2 ? i + 1 : i;
            assertThat(hasSpaceParkingLotList.get(i).getCar(ticketsList.get(i))).isSameAs(carList.get(i));
        }
        assertThat(ticketsList.size()).isEqualTo(3);

    }
    /**
     *
     *作为一个聪明的停车小弟（ Smart Parking Boy），我能够将车停在空车位最多的那个停车场
     *  given 有四个停车场，第二个停车场剩余车位最多
     *  when 停车小弟将车停到车位最多的第二停车场
     *  then 停车成功获取停车票
     */
    @Test
    public void should_return_ticket_and_stoped_in_the_second_parkinglot_when_given_four_has_spaces_and_the_second_parkingLot_has_mores_residueSpace_and_a_smartParkingBoy_valid() {
        List<ParkingLot> parkingLotList = new ArrayList();
        List<Car> carList = new ArrayList();
        create_three_cars_stop_in_four_parkinglots_and_the_second_has_more_spaces_scene(parkingLotList, carList);
        carList.clear();
        Car car = new Car();
        carList.add(car);
        parkingLotList.get(0).setResidueSpace(5);
        parkingLotList.get(1).setResidueSpace(8);
        parkingLotList.get(2).setResidueSpace(9);
        parkingLotList.get(3).setResidueSpace(7);
        ParkingBoy parkingBoy = new SmartParkingBoy();
        List<Ticket> ticketsList = parkingBoy.toParkingCar(parkingLotList, carList);
        assertThat(ticketsList.size()).isEqualTo(1);
        assertThat(parkingLotList.get(2).getCar(ticketsList.get(0))).isSameAs(car);
    }


    /**
     * 作为一个超级停车小弟（Super Parking Boy），我能够将车停在空置率最高的那个停车场。
     * given 有四个停车场，第二个停车场空置率最高
     * when 停车小弟将车停到车位空置率最高是第二个停车场
     * then 停车成功获取停车票
     */
    @Test
    public void should_return_ticket_and_stoped_in_the_second_parkinglot_when_given_four_has_spaces_and_the_second_parkingLot_has_mores_vacancy_rate_and_a_superParkingBoy_valid() {
        List<ParkingLot> parkingLotList = new ArrayList();
        List<Car> carList = new ArrayList();
        Car car = new Car();
        carList.add(car);
        ParkingLot parkingLotOne = new ParkingLot(10);
        ParkingLot parkingLotTwo = new ParkingLot(15);
        ParkingLot parkingLotThree = new ParkingLot(20);
        ParkingLot parkingLotFour = new ParkingLot(30);
        parkingLotOne.setResidueSpace(5);//空置率     50%
        parkingLotTwo.setResidueSpace(14);// 14/15    93%
        parkingLotThree.setResidueSpace(15);//15/20   75%
        parkingLotFour.setResidueSpace(20);// 20/30   66%
        parkingLotList.add(parkingLotOne);
        parkingLotList.add(parkingLotTwo);
        parkingLotList.add(parkingLotThree);
        parkingLotList.add(parkingLotFour);
        ParkingBoy parkingBoy = new SuperParkingBoy();
        List<Ticket> ticketList =  parkingBoy.toParkingCar(parkingLotList,carList);
        assertThat(ticketList.size()).isEqualTo(1);
        assertThat(ticketList.get(0)).isNotNull();
        assertThat(parkingLotList.get(1).getCar(ticketList.get(0))).isSameAs(car);


    }

    /**
     * 作为停车场的经理（Parking Manager），我要管理多个停车仔，让他们停车，同时也可以自己停车。
     * given  有车位的停车场
     * when   车场的经理（Parking Manager）去停一辆车
     * then   停车成功，获取一张停车票
     */
    @Test
    public void should_return_ticket_when_given_has_spaces_parkinglot_and_a_parking_manager_parkingCar_valid() {
        List<ParkingLot> parkingLotList = new ArrayList();
        parkingLotList.add(new ParkingLot(10));
        List<Car> carList = new ArrayList();
        carList.add(new Car());
        ParkingBoy parkingManager = new ParkingManager();
        List<Ticket> ticketList = parkingManager.toParkingCar(parkingLotList,carList);
        assertThat(ticketList.size()).isEqualTo(1);
        assertThat(ticketList.get(0)).isNotNull();
    }
    /**
     * 作为停车场的经理（Parking Manager），我要管理多个停车仔，让他们停车，同时也可以自己停车。
     * given  有车位的停车场,两个停车普通小弟
     * when   车场的经理（Parking Manager）让两个小弟停车
     * then   停车成功，获取两张停车票
     */
    @Test
    public void should_return_ticket_when_given_has_spaces_parkinglot_and_parkingMangager_let_two_graduateParkingBoy_toParkingCar_valid() {
        List<ParkingLot> parkingLotList = new ArrayList();
        parkingLotList.add(new ParkingLot(10));
        List<Car> carListOne = new ArrayList();
        carListOne.add(new Car());
        List<Car> carListTwo = new ArrayList();
        carListTwo.add(new Car());
        ParkingBoy parkingBoyOne =  new GraduateParkingBoy();
        ParkingBoy parkingBoyTwo = new GraduateParkingBoy();
        List<ParkingBoy> parkingBoyList = new ArrayList<>();
        parkingBoyList.add(parkingBoyOne);
        parkingBoyList.add(parkingBoyTwo);
        ParkingManager parkingManager = new ParkingManager(parkingBoyList,parkingLotList);
        List<Ticket> ticketListOne = parkingManager.toLetParkingBoyToParkingCar(parkingBoyOne,parkingLotList,carListOne);
        List<Ticket> ticketListTwo = parkingManager.toLetParkingBoyToParkingCar(parkingBoyTwo,parkingLotList,carListTwo);
        assertThat(ticketListOne.size()).isEqualTo(1);
        assertThat(ticketListOne.get(0)).isNotNull();
        assertThat(ticketListTwo.size()).isEqualTo(1);
        assertThat(ticketListTwo.get(0)).isNotNull();
    }
    /**
     * 作为停车场的经理（Parking Manager），我要管理多个停车仔，让他们停车，同时也可以自己停车。
     * given  有车位的停车场,两个停车小弟（1个普通小弟，一个聪明小弟）
     * when   车场的经理（Parking Manager）让两个小弟停车
     * then   停车成功，获取两张停车票,1辆车停在空车位最多的停车场
     */
    @Test
    public void should_return_ticket_when_given_has_spaces_parkinglot_and_parkingMangager_let_a_graduateParkingBoy_and_a_smartParkingBoy_toParkingCar_valid() {
        List<ParkingLot> parkingLotList = new ArrayList();
        parkingLotList.add(new ParkingLot(10));
        parkingLotList.add(new ParkingLot(20));
        List<Car> carListOne = new ArrayList();
        carListOne.add(new Car());
        List<Car> carListTwo = new ArrayList();
        carListTwo.add(new Car());
        ParkingBoy graduateParkingBoy =  new GraduateParkingBoy();
        ParkingBoy smartParkingBoy = new SmartParkingBoy();
        List<ParkingBoy> parkingBoyList = new ArrayList<>();
        parkingBoyList.add(graduateParkingBoy);
        parkingBoyList.add(smartParkingBoy);
        ParkingManager parkingManager = new ParkingManager(parkingBoyList, parkingLotList);
        List<Ticket> ticketListOne = parkingManager.toLetParkingBoyToParkingCar(graduateParkingBoy,parkingLotList,carListOne);
        List<Ticket> ticketListTwo = parkingManager.toLetParkingBoyToParkingCar(smartParkingBoy,parkingLotList,carListTwo);
        assertThat(ticketListOne.size()).isEqualTo(1);
        assertThat(ticketListOne.get(0)).isNotNull();
        assertThat(ticketListTwo.size()).isEqualTo(1);
        assertThat(ticketListTwo.get(0)).isNotNull();
        assertThat(parkingLotList.get(1).getCar(ticketListTwo.get(0))).isSameAs(carListTwo.get(0));
    }
    /**
     * 作为停车场的经理（Parking Manager），我要管理多个停车仔，让他们停车，同时也可以自己停车。
     * given  有车位的停车场4个,三个停车小弟（一个普通小弟，一个聪明小弟，一超级聪明）
     * when   车场的经理（Parking Manager）让三个小弟停车，10辆车
     * then   停车成功，获取两张停车票,1辆车停在空车位最多的停车场
     */

    private void create_three_cars_stop_in_four_parkinglots_and_the_second_has_more_spaces_scene(List<ParkingLot> parkingLotList, List<Car> carList) {
        for (int num = 1; num < 5; num++) {
            int spaces = num == 2 ? 20 : 10;
            ParkingLot parkingLot = new ParkingLot(spaces);
            parkingLotList.add(parkingLot);
            if (num == 2) continue;
            Car car = new Car();
            carList.add(car);
        }
    }
    private void create_three_cars_stop_in_four_parkinglots_and_one_is_full_scene(List<ParkingLot> parkingLotList, List<Car> carList) {
        for (int num = 1; num < 5; num++) {
            int spaces = num == 2 ? 0 : 10;
            ParkingLot parkingLot = new ParkingLot(spaces);
            parkingLotList.add(parkingLot);
            if (num == 2) continue;
            Car car = new Car();
            carList.add(car);
        }
    }
    private void create_three_cars_stop_in_three_parkinglots_scene(List<ParkingLot> parkingLotList, List<Car> carList) {
        for (int num = 0; num < 3; num++) {
            ParkingLot parkingLot = new ParkingLot(10);
            parkingLotList.add(parkingLot);
            Car car = new Car();
            carList.add(car);
        }
    }



}
