package cc.oobootcamp.principle;

import cc.oobootcamp.principle.parkingLot.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * ������ѵ��ͬѧ����2��28��֮ǰ��ɡ�ParkingLot��6������������ǰ5����ͬʱ����ɵ���ҵ�ϴ����Լ���Github�˺ţ��������ӷ������������ų�����zhangchaohui03@zybank.com.cn��
 * 1. ��Ϊһ��ͣ�������ܹ��泵��ȡ����
 * 2. ��Ϊһ������ְ����ͣ��С�ܣ�Graduate Parking Boy�������ܹ�������˳��ͣ�ŵ����ͣ������������ȡ����
 * 3. ��Ϊһ��������ͣ��С�ܣ�Smart Parking Boy�������ܹ�����ͣ�ڿճ�λ�����Ǹ�ͣ������
 * 4. ��Ϊһ������ͣ��С�ܣ�Super Parking Boy�������ܹ�����ͣ�ڿ�������ߵ��Ǹ�ͣ������
 * 5. ��Ϊͣ�����ľ���Parking Manager������Ҫ������ͣ���У�������ͣ����ͬʱҲ�����Լ�ͣ����
 * 6. ��Ϊͣ���������ܣ�Parking Director������ϣ������һ�ű������а��������ÿ��ͣ����������ĳ���
 */
public class ParkingLogTest {
    /**
     * given ��1���ճ�λ��ͣ����
     * when ͣ1����
     * then ����һ�ų�Ʊ
     */
    @Test
    public void should_return_ticket_when_parkingLot_has_one_space_valid() {
        ParkingLot parkingLot = new ParkingLot(1);
        Car car = new Car();
        Ticket ticket = parkingLot.parkingCar(car);
        assertThat(ticket).isNotNull();
    }

    /**
     * given ��ͣ��λ��ͣ����
     * when ͣ1����
     * then ͣ��ʧ��
     */
    @Test(expected = ParkingLotHasNoEnoughSpaceException.class)
    public void should_return_ParkingLotHasNoEnoughSpaceException_when_has_no_space_valid() {
        ParkingLot parkingLot = new ParkingLot(0);
        Car car = new Car();
        Ticket ticket = parkingLot.parkingCar(car);
    }

    /**
     * given ���Լ�����ͣ����
     * when ȡ������һ��ͣ��Ʊ��Ʊ�복ƥ��
     * then ȡ���ɹ�
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
     * given ���Լ�����ͣ����
     * when ȡ������һ��ͣ��Ʊ��Ʊ�복��ƥ��
     * then ȡ�����ɹ�
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
     * given ���Լ�����ͣ����
     * when ͬһ��Ʊȡ���܇
     * then ȡ�����ɹ�
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
     * ��Ϊһ������ְ����ͣ��С�ܣ�Graduate Parking Boy�������ܹ�������˳��ͣ�ŵ����ͣ������������ȡ��
     * given �������г�λ��ͣ����
     * when ͣ��С��ͣ������
     * then �ɹ�����˳��ͣ��,����ȡ����ͣ��Ʊ��ÿ��ͣ����ʣ�೵λ -1��ͣ��������+1
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
     * ��Ϊһ������ְ����ͣ��С�ܣ�Graduate Parking Boy�������ܹ�������˳��ͣ�ŵ����ͣ������������ȡ��
     * given �������г�λ��ͣ������ͣ��С���Ѿ�ͣ�˰���˳��ͣ��������
     * when С����Ʊ�ɹ�ȡ������
     * then �ɹ�ȡ��
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
     * ��Ϊһ������ְ����ͣ��С�ܣ�Graduate Parking Boy�������ܹ�������˳��ͣ�ŵ����ͣ������������ȡ��
     * given ���ĸ�ͣ�������ڶ���ͣ�����޳�λ,�Ѿ�ͣ��������
     * when С����Ʊȡ��
     * then �ɹ�ȡ������
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
     * ��Ϊһ������ְ����ͣ��С�ܣ�Graduate Parking Boy�������ܹ�������˳��ͣ�ŵ����ͣ������������ȡ��
     * given ���ĸ�ͣ������������ͣ�����޳�λ
     * when ͣ��С��ͣ������
     * then �ɹ�����˳��ͣ��,����ȡ����ͣ��Ʊ
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
     *��Ϊһ��������ͣ��С�ܣ� Smart Parking Boy�������ܹ�����ͣ�ڿճ�λ�����Ǹ�ͣ����
     *  given ���ĸ�ͣ�������ڶ���ͣ����ʣ�೵λ���
     *  when ͣ��С�ܽ���ͣ����λ���ĵڶ�ͣ����
     *  then ͣ���ɹ���ȡͣ��Ʊ
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
     * ��Ϊһ������ͣ��С�ܣ�Super Parking Boy�������ܹ�����ͣ�ڿ�������ߵ��Ǹ�ͣ������
     * given ���ĸ�ͣ�������ڶ���ͣ�������������
     * when ͣ��С�ܽ���ͣ����λ����������ǵڶ���ͣ����
     * then ͣ���ɹ���ȡͣ��Ʊ
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
        parkingLotOne.setResidueSpace(5);//������     50%
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
     * ��Ϊͣ�����ľ���Parking Manager������Ҫ������ͣ���У�������ͣ����ͬʱҲ�����Լ�ͣ����
     * given  �г�λ��ͣ����
     * when   �����ľ���Parking Manager��ȥͣһ����
     * then   ͣ���ɹ�����ȡһ��ͣ��Ʊ
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
     * ��Ϊͣ�����ľ���Parking Manager������Ҫ������ͣ���У�������ͣ����ͬʱҲ�����Լ�ͣ����
     * given  �г�λ��ͣ����,����ͣ����ͨС��
     * when   �����ľ���Parking Manager��������С��ͣ��
     * then   ͣ���ɹ�����ȡ����ͣ��Ʊ
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
     * ��Ϊͣ�����ľ���Parking Manager������Ҫ������ͣ���У�������ͣ����ͬʱҲ�����Լ�ͣ����
     * given  �г�λ��ͣ����,����ͣ��С�ܣ�1����ͨС�ܣ�һ������С�ܣ�
     * when   �����ľ���Parking Manager��������С��ͣ��
     * then   ͣ���ɹ�����ȡ����ͣ��Ʊ,1����ͣ�ڿճ�λ����ͣ����
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
     * ��Ϊͣ�����ľ���Parking Manager������Ҫ������ͣ���У�������ͣ����ͬʱҲ�����Լ�ͣ����
     * given  �г�λ��ͣ����4��,����ͣ��С�ܣ�һ����ͨС�ܣ�һ������С�ܣ�һ����������
     * when   �����ľ���Parking Manager��������С��ͣ����10����
     * then   ͣ���ɹ�����ȡ����ͣ��Ʊ,1����ͣ�ڿճ�λ����ͣ����
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
