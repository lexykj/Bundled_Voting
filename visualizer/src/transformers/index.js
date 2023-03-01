import { data } from "../data";

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
            if (!counts.has(first.name)) {
                counts.set(first.name, 0);
            }
            counts.set(first.name, counts.get(first.name) + 1);
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
            if (!counts.has(movie.name)) {
                counts.set(movie.name, 0);
            }
            counts.set(movie.name, counts.get(movie.name)+1);
        }
    }
    return getSortedRankingFromCounts(counts);
}

export function getBordaCounts() {
    const counts = new Map();
    for (let k in data) {
        data[k].forEach((movie, i) => {
            if (!counts.has(movie.name)) {
                counts.set(movie.name, 0);
            }
            counts.set(movie.name, counts.get(movie.name)+ data[k].length - i);
        });
    }
    return getSortedRankingFromCounts(counts);
}