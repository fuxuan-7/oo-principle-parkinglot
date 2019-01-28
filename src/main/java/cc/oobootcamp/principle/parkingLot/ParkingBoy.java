package cc.oobootcamp.principle.parkingLot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ParkingBoy {
    public abstract List<Ticket> toParkingCar(List<ParkingLot> parkingLotList, List<Car> carList);

    public List<Car> toGetParkingCar(List<ParkingLot> parkingLotList, List<Ticket> ticketList) {
        List<Car> carList = new ArrayList<>();
        for (int i = 0; i < ticketList.size(); i++) {
            for (int k = 0; k < parkingLotList.size(); k++) {
                try {
                    Car car = parkingLotList.get(k).getCar(ticketList.get(i));
                    carList.add(car);
                    break;
                } catch (ParkingLotGetCarFailedException e) {
                }
            }
        }
        return carList;
    }
}
