package io.github.rothschil.controller;

import io.github.rothschil.base.persistence.jpa.repository.BaseRepository;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/15 - 16:41
 * @since 1.0.0
 */

public interface UserRepository extends BaseRepository<User, Long> {

    User getUserById(Long id);

}
