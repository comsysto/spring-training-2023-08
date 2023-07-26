package com.comsysto.trainings.springtrainingeon.fluxapp.domain.common;

import lombok.Value;

@Value
public class Address {
    String street;
    String houseNumber;
    String city;
    String zip;
    String country;
}
