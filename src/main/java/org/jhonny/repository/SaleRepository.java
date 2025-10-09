package org.jhonny.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.jhonny.models.Employees;
import org.jhonny.models.Sales;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class SaleRepository extends EntityRepository<Sales> {
    private String query;

    /// 1
    public Long getAllSalesBetweenDates(LocalDate startDate, LocalDate endDate) {
        query = """
                SELECT COUNT(*)
                FROM Sales s INNER JOIN Tickets t ON s.id = t.saleId
                INNER JOIN TicketDetails td ON t.id = td.ticketId
                WHERE td.dateOfGame BETWEEN ? AND ?;
                """;

        Number result = (Number) getEntityManager()
                .createNativeQuery(query)
                .setParameter(1, startDate)
                .setParameter(2, endDate)
                .getSingleResult();

        return result.longValue();
    }

    public Long getNumberOfSalesInAllGamesBetweenDatesJPQL(LocalDate startDate, LocalDate endDate) {
        query = """
                SELECT COUNT(td)
                FROM TicketDetails td
                JOIN td.ticket t
                JOIN t.sale s
                WHERE td.dateOfGame BETWEEN :startDate AND :endDate
            """;

        return getEntityManager()
                .createQuery(query, Long.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getSingleResult();

    }

    /// 2
    public Long getNumberOfSalesIntoASpecificGameAndRangeDate(Long gameId, LocalDate startDate, LocalDate endDate) {
        query = """
                SELECT COUNT(*)
                FROM Sales s INNER JOIN Tickets t ON s.id = t.saleId
                INNER JOIN TicketDetails td ON t.id = td.ticketId
                WHERE td.gameId = ? AND td.dateOfGame BETWEEN ? AND ?;
                """;

        Number result = (Number) getEntityManager()
                .createNativeQuery(query)
                .setParameter(1, gameId)
                .setParameter(2, startDate)
                .setParameter(3, endDate)
                .getSingleResult();

        return result.longValue();
    }

    public Long getNumberOfSalesIntoASpecificGameAndRangeDateJPQL(Long gameId, LocalDate startDate, LocalDate endDate) {
        query = """
                SELECT COUNT(td)
                FROM TicketDetails td
                JOIN td.ticket t
                JOIN t.sale s
                WHERE td.game.id = :gameId AND td.dateOfGame BETWEEN :startDate AND :endDate
                """;

        return getEntityManager()
                .createQuery(query, Long.class)
                .setParameter("gameId", gameId)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getSingleResult();
    }

    /// 3
    public BigDecimal totalSumOfSalesAmountInASingleDay(LocalDate date) {
        query = """
                SELECT SUM(td.price)
                FROM Sales s INNER JOIN Tickets t ON s.id = t.saleId
                INNER JOIN TicketDetails td ON t.id = td.ticketId
                WHERE td.dateOfGame = ?;
                """;

        return (BigDecimal) getEntityManager()
                .createNativeQuery(query)
                .setParameter(1, date)
                .getSingleResult();
    }

    public BigDecimal getTotalSumOfSalesAmountInASingleDayJPQL(LocalDate date) {
        query = """
                 SELECT SUM(td.price)
                FROM TicketDetails td
                JOIN td.ticket t
                JOIN t.sale s
                WHERE td.dateOfGame = :dateOfGame
                """;

        return (BigDecimal) getEntityManager()
                .createQuery(query)
                .setParameter("dateOfGame", date)
                .getSingleResult();
    }

    /// 4
    public BigDecimal getTotalSumOfSalesAmountInASingleMonth(LocalDate date) {
        int month = date.getMonth().getValue();
        int year = date.getYear();
        query = """
                SELECT SUM(td.price)
                FROM Sales s
                INNER JOIN Tickets t ON s.id = t.saleId
                INNER JOIN TicketDetails td ON t.id = td.ticketId
                WHERE MONTH(td.dateOfGame) = ?
                	AND YEAR(td.dateOfGame) = ?;
                """;

        return (BigDecimal) getEntityManager()
                .createNativeQuery(query)
                .setParameter(1, month)
                .setParameter(2, year)
                .getSingleResult();
    }

    /// 5
    public BigDecimal getTotalSumOfSalesAmountInASingleYear(LocalDate date) {
        int year = date.getYear();
        query = """
                SELECT SUM(td.price)
                FROM Sales s INNER JOIN Tickets t ON s.id = t.saleId
                INNER JOIN TicketDetails td ON t.id = td.ticketId
                WHERE YEAR(td.dateOfGame) = ?;
                """;

        return (BigDecimal) getEntityManager()
                .createNativeQuery(query)
                .setParameter(1, year)
                .getSingleResult();
    }


    /// 7
    public Object[] getTheBestSellerIntoRangeOfDates(LocalDate date) {
        int month = date.getMonth().getValue();
        int year = date.getYear();
        query = """
                SELECT t.buyerId, COUNT(td.id)
                FROM Sales s
                INNER JOIN Tickets t ON s.id = t.saleId
                INNER JOIN TicketDetails td ON t.id = td.ticketId
                WHERE MONTH(td.dateOfGame) = ?
                	AND YEAR(td.dateOfGame) = ?
                GROUP BY t.buyerId
                ORDER BY totalAmount DESC
                LIMIT 1;
                """;

        return (Object[]) getEntityManager()
                .createNativeQuery(query)
                .setParameter(1, month)
                .setParameter(2, year)
                .getSingleResult();
    }
}