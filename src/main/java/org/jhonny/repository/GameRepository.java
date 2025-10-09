package org.jhonny.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.jhonny.models.Games;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class GameRepository extends EntityRepository<Games> {
    private static final Logger LOG = LoggerFactory.getLogger(GameRepository.class);
    private String query;

    public Games getTheBestSellingGame(){
        query = """
                SELECT td.gameId
                FROM Sales s
                INNER JOIN Tickets t ON s.id = t.saleId
                INNER JOIN TicketDetails td ON t.id = td.ticketId
                INNER JOIN Games g on td.gameId = g.id
                GROUP BY td.gameId
                ORDER BY COUNT(td.gameId) DESC
                LIMIT 1;
                """;

        Number number = (Number) getEntityManager().createNativeQuery(query).getSingleResult();
        Games theBestSellingGame = findById(number.longValue());
        return theBestSellingGame;
    }
}
