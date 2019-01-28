package cc.oobootcamp.principle.parkingLot;


import java.util.HashMap;
import java.util.Map;

public class ParkingLot {
    private int space = 0;

    private Map<Ticket, Car> parkedCars = new HashMap();

    private int residueSpace = 0;

    public ParkingLot(int space) {
        this.space = space;
        residueSpace = space;
    }

    public Ticket parkingCar(Car car) throws ParkingLotHasNoEnoughSpaceException {
        if (hasNoEnoughSpaces()) {
            throw new ParkingLotHasNoEnoughSpaceException();
        }
        Ticket ticket = null;
        if (car != null){
            ticket = new Ticket();
            parkedCars.put(ticket, car);
            if (residueSpace > 0) {
                residueSpace--;
            }
        }
        return ticket;
    }

    public Car getCar(Ticket ticket) throws ParkingLotGetCarFailedException {
        Car car = parkedCars.remove(ticket);
        if (car == null) {
            throw new ParkingLotGetCarFailedException();
        }
        if (residueSpace < space) {
            residueSpace ++;
        }
        return car;
    }

    public double parkingLotVacancyRate() {
        return residueSpace / Double.valueOf(space) * 100 ;
    }

    public boolean hasNoEnoughSpaces(){
        return  residueSpace <= 0 ;
    }

    public int getResidueSpace() {
        return residueSpace;
    }

    public void setResidueSpace(int residueSpace) {
        this.residueSpace = residueSpace;
    }

    public Map<Ticket, Car> getParkedCars() {
        return parkedCars;
    }


    public int getSpace() {
        return space;
    }

//    public int getSpace() {
//        return space;
//    }
}
