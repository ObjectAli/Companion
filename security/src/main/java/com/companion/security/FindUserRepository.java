package com.companion.security;

import com.companion.jooq.generated.tables.pojos.Users;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.companion.jooq.generated.tables.Users.USERS;

@Repository
public class FindUserRepository {

    private final DSLContext dslContext;

    public FindUserRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public Optional<Users> find(String name) {
        return dslContext
                .select()
                .from(USERS)
                .where(USERS.USERNAME.eq(name))
                .fetchOptionalInto(Users.class);
    }
}
