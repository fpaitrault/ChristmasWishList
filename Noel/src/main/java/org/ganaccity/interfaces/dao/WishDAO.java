package org.ganaccity.interfaces.dao;

import java.util.List;

import org.ganaccity.mdl.Wish;

public interface WishDAO extends GenericDAO<Wish> {
    List<Wish> getHotWishes();
}
