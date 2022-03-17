package main.parkinglot;

import java.util.List;

public class CommandExecutor {
    private static CommandExecutor commandExecutor;

    private CommandExecutor() {

    }

    public static CommandExecutor getInstance() {
        if (commandExecutor == null) {
            commandExecutor = new CommandExecutor();
        }
        return commandExecutor;
    }

    private CommandName getCommandName(String commandString) {

        CommandName commandName = null;

        if (commandString == null) {
            System.out.println("Not a valid input");
        } else {
            String[] commandStringArray = commandString.split(" ");
            if ("".equals(commandStringArray[0])) {
                System.out.println("Not a valid input");
            } else {
                try {
                    commandName = CommandName.valueOf(commandStringArray[0]);
                } catch (Exception e) {
                    System.out.println("Unknown Command");
                }
            }
        }
        return commandName;

    }

    public boolean execute(String commandString) {

        CommandName commandName = getCommandName(commandString);

        if (commandName == null) {
            return false;
        }
        String[] commandStringArray = commandString.split(" ");
        Command command;

        switch (commandName) {
            case create_parking_lot:
                command = new CreateParkingLotCommand(commandStringArray);
                break;
            case park:
                command = new ParkCommand(commandStringArray);
                break;
            case leave:
                command = new LeaveCommand(commandStringArray);
                break;
            case status:
                command = new StatusCommand(commandStringArray);
                break;
            case registration_numbers_for_cars_with_colour:
                command = new RegistrationNumbersForColorCommand(commandStringArray);
                break;
            case slot_numbers_for_cars_with_colour:
                command = new SlotNumbersForColorCommand(commandStringArray);
                break;
            case slot_number_for_registration_number:
                command = new SlotNumberCommand(commandStringArray);
                break;
            case show_abonament_cars:
                command = new AbonamentCarsCommand(commandStringArray);
                break;
            case show_noabonament_cars:
                command = new NoAbonamentCarsCommand(commandStringArray);
                break;
            default:
                System.out.println("Unknown Command");
                return false;
        }

        try {
            command.validate();
        } catch (IllegalArgumentException e) {
            System.out.println("Please provide a valid argument");
            return false;
        }

        String output = "";
        try {
            output = command.execute();
        } catch (Exceptions e) {
            System.out.print(e.getMessage());
        } catch(Exception e) {
            System.out.println("Unknown System Issue");
            e.printStackTrace();
            return false;
        }
        System.out.println(output);
        return true;
    }

    private enum CommandName {
        create_parking_lot, park, leave, status, registration_numbers_for_cars_with_colour,
        slot_numbers_for_cars_with_colour, slot_number_for_registration_number,show_abonament_cars,show_noabonament_cars
    }

    private interface Command {
        public void validate();

        public String execute();
    }

    private class CreateParkingLotCommand implements Command {
        private String[] commandStringArray;

        CreateParkingLotCommand(String[] s) {
            commandStringArray = s;
        }

        public void validate() {
            if (commandStringArray.length != 2) {
                throw new IllegalArgumentException("create_parking_lot command should have exactly 1 argument");
            }
        }

        public String execute() {
            int numberOfSlots = Integer.parseInt(commandStringArray[1]);
            TicketingSystem.createInstance(numberOfSlots);
            return "Created a parking lot with " + commandStringArray[1] + " slots";
        }
    }

    private class ParkCommand implements Command {
        private String[] commandStringArray;

        ParkCommand(String[] s) {
            commandStringArray = s;
        }

        public void validate() {
            if (commandStringArray.length != 3&&commandStringArray.length!=4) {
                throw new IllegalArgumentException("park command should have exactly 2 or 3 arguments");
            }
        }

        public String execute() {
            TicketingSystem ticketingSystem = TicketingSystem.getInstance();
            int allocatedSlotNumber = ticketingSystem
                    .issueParkingTicket(new Car(commandStringArray[1], commandStringArray[2],commandStringArray[3]));
            return "Allocated slot number: " + allocatedSlotNumber;
        }
    }

    private class LeaveCommand implements Command {
        private String[] commandStringArray;

        LeaveCommand(String[] s) {
            commandStringArray = s;
        }

        public void validate() {
            if (commandStringArray.length != 2) {
                throw new IllegalArgumentException("leave command should have exactly 1 argument");
            }
        }

        public String execute() {
            TicketingSystem ticketingSystem = TicketingSystem.getInstance();
            ticketingSystem.exitVehicle(Integer.parseInt(commandStringArray[1]));
            return "Slot number " + commandStringArray[1] + " is free";
        }
    }

    private class StatusCommand implements Command {
        private String[] commandStringArray;

        StatusCommand(String[] s) {
            commandStringArray = s;
        }

        public void validate() {
            if (commandStringArray.length != 1) {
                throw new IllegalArgumentException("status command should have no arguments");
            }
        }

        public String execute() {
            TicketingSystem ticketingSystem = TicketingSystem.getInstance();
            List<StatusResponse> statusResponseList = ticketingSystem.getStatus();

            StringBuilder outputStringBuilder = new StringBuilder("Slot No.  Registration No  Color  Abonament Info");
            for (StatusResponse statusResponse : statusResponseList) {
                outputStringBuilder.append("\n").append(statusResponse);
            }
            return outputStringBuilder.toString();
        }
    }
    private class AbonamentCarsCommand implements Command {
        private String[] commandStringArray;

        AbonamentCarsCommand(String[] s) {
            commandStringArray = s;
        }

        public void validate() {
            if (commandStringArray.length != 1) {
                throw new IllegalArgumentException("AbonamentCars command should have no arguments");
            }
        }

        public String execute() {
            TicketingSystem ticketingSystem = TicketingSystem.getInstance();
            List<StatusResponse> statusResponseList = ticketingSystem.getStatus();

            StringBuilder outputStringBuilder = new StringBuilder("Slot No.  Registration No  Colour  Abonament Info");
            for (StatusResponse statusResponse : statusResponseList) {
                if(statusResponse.getAbonamentInfo().equals("yes")) {
                    outputStringBuilder.append("\n").append(statusResponse);
                }
            }
            return outputStringBuilder.toString();
        }
    }
    private class NoAbonamentCarsCommand implements Command {
        private String[] commandStringArray;

        NoAbonamentCarsCommand(String[] s) {
            commandStringArray = s;
        }

        public void validate() {
            if (commandStringArray.length != 1) {
                throw new IllegalArgumentException("NoAbonamentCars command should have no arguments");
            }
        }

        public String execute() {
            TicketingSystem ticketingSystem = TicketingSystem.getInstance();
            List<StatusResponse> statusResponseList = ticketingSystem.getStatus();

            StringBuilder outputStringBuilder = new StringBuilder("Slot No.    Registration No    Colour   Abonament Info");
            for (StatusResponse statusResponse : statusResponseList) {
                if(statusResponse.getAbonamentInfo().equals("no")) {
                    outputStringBuilder.append("\n").append(statusResponse);
                }
            }
            return outputStringBuilder.toString();
        }
    }

    private class RegistrationNumbersForColorCommand implements Command {
        private String[] commandStringArray;

        RegistrationNumbersForColorCommand(String[] s) {
            commandStringArray = s;
        }

        public void validate() {
            if (commandStringArray.length != 2) {
                throw new IllegalArgumentException(
                        "registration_numbers_for_cars_with_colour command should have exactly 1 argument");
            }
        }

        public String execute() {
            TicketingSystem ticketingSystem = TicketingSystem.getInstance();
            List<String> registrationNumbersList = ticketingSystem
                    .getRegistrationNumbersFromColor(commandStringArray[1]);
            StringBuilder outputStringBuilder = new StringBuilder();
            for (String registrationNumber : registrationNumbersList) {
                if (outputStringBuilder.length() > 0) {
                    outputStringBuilder.append(", ");
                }
                outputStringBuilder.append(registrationNumber);
            }
            return outputStringBuilder.toString();
        }
    }

    private class SlotNumbersForColorCommand implements Command {
        private String[] commandStringArray;

        SlotNumbersForColorCommand(String[] s) {
            commandStringArray = s;
        }

        public void validate() {
            if (commandStringArray.length != 2) {
                throw new IllegalArgumentException(
                        "slot_numbers_for_cars_with_colour command should have exactly 1 argument");
            }
        }

        public String execute() {
            TicketingSystem ticketingSystem = TicketingSystem.getInstance();
            List<Integer> slotNumbersList = ticketingSystem.getSlotNumbersFromColor(commandStringArray[1]);
            StringBuilder outputStringBuilder = new StringBuilder();
            for (int slotNumber : slotNumbersList) {
                if (outputStringBuilder.length() > 0) {
                    outputStringBuilder.append(", ");
                }
                outputStringBuilder.append(slotNumber);
            }
            return outputStringBuilder.toString();
        }
    }

    private class SlotNumberCommand implements Command {
        private String[] commandStringArray;

        SlotNumberCommand(String[] s) {
            commandStringArray = s;
        }

        public void validate() {
            if (commandStringArray.length != 2) {
                throw new IllegalArgumentException(
                        "slot_number_for_registration_number command should have exactly 1 argument");
            }
        }

        public String execute() {
            TicketingSystem ticketingSystem = TicketingSystem.getInstance();
            int slotNumber = ticketingSystem.getSlotNumberFromRegistrationNumber(commandStringArray[1]);
            return "" + slotNumber;
        }
    }

}
