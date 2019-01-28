package cc.oobootcamp.principle.parkingLot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SuperParkingBoy extends ParkingBoy {

    @Override
    public List<Ticket> toParkingCar(List<ParkingLot> parkingLotList, List<Car> carList) {
        List<Ticket> ticketsList = new ArrayList();
        //作为一个聪明的停车小弟（ SmartParking Boy），我能够将车停在空车位空置率的那个停车场
        List<ParkingLot> hasSpaceParkingLotList = parkingLotList.stream().sorted(Comparator.comparing(ParkingLot::parkingLotVacancyRate).reversed()
        ).collect(Collectors.toList());
        for(int i=0;i<carList.size();i++){
            int parkingLotNum = i + 1 <=hasSpaceParkingLotList.size() ?  i :  i % hasSpaceParkingLotList.size();
            ticketsList.add(hasSpaceParkingLotList.get(parkingLotNum).parkingCar(carList.get(i)));
        }
        return ticketsList;
    }
}
