package com.techprimers.stock.dbservice.resource;

import com.sun.org.apache.xpath.internal.operations.Quo;
import com.techprimers.stock.dbservice.model.Quote;
import com.techprimers.stock.dbservice.model.Quotes;
import com.techprimers.stock.dbservice.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/db")
public class DBServiceResource {


    private QuoteRepository quoteRepository;

    @Autowired
    public DBServiceResource(QuoteRepository quoteRepository){
        this.quoteRepository = quoteRepository;
    }

    @GetMapping("/{username}")
    public List<String> getQuotes(@PathVariable("username") final String username){
        return getQuotesByUserName(username);
    }

    private List<String> getQuotesByUserName(String username) {
        return quoteRepository.findByUserName(username)
                .stream()
                .map(Quote::getQuote)
                .collect(Collectors.toList());
    }

    @PostMapping("/add")
    public List<String> add(@RequestBody final Quotes quotes){
        quotes.getQuotes()
                .stream()
                .map(quote -> new Quote(quotes.getUserName(), quote))
                .forEach(quote -> quoteRepository.save(quote));

        return getQuotesByUserName(quotes.getUserName());
    }

    @PostMapping("/delete/{username}")
    public List<String> delete(@PathVariable("username") final String username) {

        List<Quote> quotes = quoteRepository.findByUserName(username);
        quotes.stream().forEach(quote -> quoteRepository.delete(quote));

        return getQuotesByUserName(username);
    }
}
