package cc.oobootcamp.principle.parkingLot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ParkingManager extends ParkingBoy {

    private ArrayList<ParkingBoy> parkingBoysList = new ArrayList<ParkingBoy>();
    private ArrayList<ParkingLot> parkingLotsList = new ArrayList<ParkingLot>();

    public ParkingManager(){

    }

    public ParkingManager(List<ParkingBoy> parkingBoyList, List<ParkingLot> parkingLotList) {
        this.parkingBoysList = parkingBoysList;
        this.parkingLotsList = parkingLotsList;
    }

    @Override
    public List<Ticket> toParkingCar(List<ParkingLot> parkingLotList, List<Car> carList) {
        List<Ticket> ticketsList = new ArrayList();
        List<ParkingLot> hasSpaceParkingLotList = parkingLotList.stream().filter(parkingLot -> parkingLot.getResidueSpace() >0 ).collect(Collectors.toList());
        for(int i=0;i<carList.size();i++){
            int parkingLotNum = i + 1 <=hasSpaceParkingLotList.size() ?  i :  i % hasSpaceParkingLotList.size();
            ticketsList.add(hasSpaceParkingLotList.get(parkingLotNum).parkingCar(carList.get(i)));
        }
        return ticketsList;
    }

    public List<Ticket> toLetParkingBoyToParkingCar(ParkingBoy parkingBoy, List<ParkingLot> parkingLotList, List<Car> carList) {
        return parkingBoy.toParkingCar(parkingLotList,carList);
    }


}
