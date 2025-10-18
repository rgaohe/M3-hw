package dogapi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CachingBreedFetcher implements BreedFetcher {
    private final BreedFetcher delegate;
    private final Map<String, List<String>> cache = new HashMap<>();
    private int callsMade = 0;

    public CachingBreedFetcher(BreedFetcher fetcher) {
        this.delegate = fetcher;
    }

    @Override
    public List<String> getSubBreeds(String breed) throws BreedNotFoundException {
        if (breed == null) {
            throw new BreedNotFoundException("null");
        }

        String key = breed.toLowerCase().trim();
        if (cache.containsKey(key)) {
            return cache.get(key);
        }

        callsMade++;
        List<String> subs = delegate.getSubBreeds(key);
        cache.put(key, subs);
        return subs;
    }

    public int getCallsMade() {
        return callsMade;
    }
}