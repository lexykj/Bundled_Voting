import { data } from "../data";

/**
 * Unescape escaped characters
 * @param {string} name 
 */
function formatMovieName(name) {
    return name.replace(/&#44;/, ',');
}

/**
 * 
 * @param {Map<string, number>} counts 
 * @returns {[string, number][]}
 */
function getSortedRankingFromCounts(counts) {
    const entries = [...counts];
    entries.sort(([,c1], [,c2]) => c2 - c1);
    return entries;
}

/**
 * Returns the number of first place votes
 * @returns {[string, number][]}
 */
export function getCountsOfFirstPlaceVotes() {
    const counts = new Map();
    for (let k in data) {
        const first = data[k][0];
        if (first?.name) {
            if (!counts.has(formatMovieName(first.name))) {
                counts.set(formatMovieName(first.name), 0);
            }
            counts.set(formatMovieName(first.name), counts.get(formatMovieName(first.name)) + 1);
        }
    }
    return getSortedRankingFromCounts(counts);
}

/**
 * Returns the number of votes
 * @returns {[string, number][]}
 */
export function getCountsOfVotes() {
    const counts = new Map();
    for (let k in data) {
        for (let movie of data[k]) {
            if (!counts.has(formatMovieName(movie.name))) {
                counts.set(formatMovieName(movie.name), 0);
            }
            counts.set(formatMovieName(movie.name), counts.get(formatMovieName(movie.name))+1);
        }
    }
    return getSortedRankingFromCounts(counts);
}

export function getBordaCounts() {
    const counts = new Map();
    for (let k in data) {
        data[k].forEach((movie, i) => {
            if (!counts.has(formatMovieName(movie.name))) {
                counts.set(formatMovieName(movie.name), 0);
            }
            counts.set(formatMovieName(movie.name), counts.get(formatMovieName(movie.name))+ data[k].length - i);
        });
    }
    return getSortedRankingFromCounts(counts);
}