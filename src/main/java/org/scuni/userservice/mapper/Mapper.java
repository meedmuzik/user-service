package org.scuni.userservice.mapper;

public interface Mapper<F, T> {
    T map(F object);
}
