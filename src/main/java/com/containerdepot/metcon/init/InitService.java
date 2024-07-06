package com.containerdepot.metcon.init;

import com.containerdepot.metcon.data.*;
import com.containerdepot.metcon.model.entities.*;
import com.containerdepot.metcon.model.enums.UserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Component
public class InitService implements CommandLineRunner {
    private final Set<Location> locations = new HashSet<Location>(Set.of(
            new Location("Bulgaria", "Varna", 9000),
            new Location("Bulgaria", "Burgas", 8000),
            new Location("Bulgaria", "Sofia", 1000),
            new Location("Bulgaria", "Plovdiv", 4000),
            new Location("Bulgaria", "Veliko Tarnovo", 5000),
            new Location("Bulgaria", "Stara Zagora", 6000),
            new Location("Bulgaria", "Shumen", 9700),
            new Location("Bulgaria", "Targovishte", 7700),
            new Location("Bulgaria", "Ruse", 7000),
            new Location("Bulgaria", "Pleven", 5800),
            new Location("Bulgaria", "Lovech", 5500)
    ));
    private final Set<Truck> trucks = new HashSet<Truck>(Set.of(
            new Truck("B1045HH"),
            new Truck("B5687TA"),
            new Truck("B6421CH"),
            new Truck("B1300HT"),
            new Truck("CB1045HH"),
            new Truck("H1045TT")
    ));
    private final Set<Driver> drivers = new HashSet<>(Set.of(
            new Driver("Ivan Ivanov", "+359878 134 512"),
            new Driver("Mihail Kosev", "+359888 000 556"),
            new Driver("Simeon Georgiev", "+359873 147 874"),
            new Driver("Nikola Ivanov", "+359874 111 744"),
            new Driver("Georgi Milushev", "+359878 134 887"),
            new Driver("Swetlin Hristov", "+359876 654 747")
    ));
    private final List<Role> roles = new ArrayList<>(List.of(
            new Role(UserRole.USER),
            new Role(UserRole.MODERATOR),
            new Role(UserRole.ADMIN)
            ));

    private final Company company = new Company("MET", "МЕТ", 154689941, "Ivan Shishman 30 str.", "met-depot@metcontainer.com", "+35952 150 148");
    private final LocationRepository locationRepository;
    private final TruckRepository truckRepository;
    private final DriverRepository driverRepository;
    private final CompanyRepository companyRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public InitService(LocationRepository locationRepository,
                       TruckRepository truckRepository,
                       DriverRepository driverRepository,
                       CompanyRepository companyRepository,
                       RoleRepository roleRepository, UserRepository userRepository) {
        this.locationRepository = locationRepository;
        this.truckRepository = truckRepository;
        this.driverRepository = driverRepository;
        this.companyRepository = companyRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        initLocations();
        initTrucks();
        initDrivers();
        initMotherCompany();
        initRoles();
        initAdmin();
    }
    private void initLocations() {
        if (this.locationRepository.count() > 0) {
            return;
        }
        this.locationRepository.saveAll(locations);
    }
    private void initTrucks() {
        if (this.truckRepository.count() > 0) {
            return;
        }
        this.truckRepository.saveAll(trucks);
    }
    private void initDrivers() {
        if (this.driverRepository.count() > 0) {
            return;
        }
        this.driverRepository.saveAll(drivers);
    }
    private void initMotherCompany() {
        if (this.companyRepository.count() > 0) {
            return;
        }
        Location city = this.locationRepository.findByCity("Varna").get();
        this.company.setCity(city);
        this.companyRepository.save(company);
    }
    private void initRoles() {
        if (this.roleRepository.count() > 0) {
            return;
        }
        this.roleRepository.saveAll(roles);
    }
    private void initAdmin() {
        if (this.userRepository.count() > 0) {
            return;
        }
        User admin = new User("admin",
                "admin",
                new HashSet<>(Set.of(this.roleRepository.findByRole(UserRole.ADMIN))),
                "admin",
                "admin",
                "admin@mail.com",
                this.companyRepository.findById(1l).get());
        this.userRepository.save(admin);
    }
}
