package ru.practicum.ewm.repository;

import org.springframework.data.repository.CrudRepository;
import ru.practicum.ewm.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {

}
