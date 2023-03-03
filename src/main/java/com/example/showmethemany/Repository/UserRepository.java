package com.example.showmethemany.Repository;

import com.example.showmethemany.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Member, Long> {
}
