package cc.oobootcamp.principle.parkingLot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SmartParkingBoy extends ParkingBoy {


    @Override
    public List<Ticket> toParkingCar(List<ParkingLot> parkingLotList, List<Car> carList) {
        List<Ticket> ticketsList = new ArrayList();
        //作为一个聪明的停车小弟（ SmartParking Boy），我能够将车停在空车位最多的那个停车场
        List<ParkingLot> hasSpaceParkingLotList = parkingLotList.stream().filter(parkingLot -> parkingLot.getResidueSpace() >0 ).sorted(
                Comparator.comparing(ParkingLot::getResidueSpace).reversed()
        ).collect(Collectors.toList());
        for(int i=0;i<carList.size();i++){
            int parkingLotNum = i + 1 <=hasSpaceParkingLotList.size() ?  i :  i % hasSpaceParkingLotList.size();
            ticketsList.add(hasSpaceParkingLotList.get(parkingLotNum).parkingCar(carList.get(i)));
        }
        return ticketsList;
    }


}
