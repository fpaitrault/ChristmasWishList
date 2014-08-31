package org.fpaitrault.interfaces.dao;

import java.util.List;

import org.fpaitrault.mdl.Wish;

public interface WishDAO extends GenericDAO<Wish> {
    List<Wish> getHotWishes();
}
