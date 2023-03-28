const { parse } = require('node-html-parser');
const fs = require('fs/promises');
const kebabCase = require('just-kebab-case');


/**
 * Gets an array of countries kebab cased
 * @returns {string[]}
 */
async function getCountries() {
    return await fetch('https://www.worldometers.info/geography/alphabetical-list-of-countries/')
        .then(d => d.text())
        .then(d => parse(d))
        .then(d => d.querySelectorAll('tr > td:nth-child(2)'))
        .then(d => d.map(e => kebabCase(e.innerText)))
        .catch(() => []);
}

/**
 * Gets the netflix top 10 ranking for the specified country
 * @param {string} country in kebab case
 * @returns {any[]}
 */
async function getCountryRanking(country) {
    return await fetch(`https://top10.netflix.com/${country}/tv`)
        .then(d => d.text())
        .then(d => parse(d))
        .then(d => d.querySelector('#__NEXT_DATA__'))
        .then(d => d.innerText)
        .then(d => JSON.parse(d))
        .then(d => d.props.pageProps.data.weeklyTopTen)
        .catch(() => []);
}

async function scrapeRankings() {
    const countries = await getCountries();
    const rankings = {};
    /**
     * Adds ranking to object
     * @param {string} country 
     */
    async function mapCountryToRankingObject(country) {
        const data = await getCountryRanking(country);
        rankings[country] = data;
    }
    await Promise.all(countries.map(mapCountryToRankingObject));
    return rankings;
}

/**
 * 
 * @param {{
 * [k: string]: any[]
 * }} rankings 
 */
async function writeRankingsToFile(rankings) {
    try {
        fs.mkdir('./data');
    } catch {
        fs.rmdir('./data');
    }
    for (country in rankings) {
        const columns = ['id', 'showId', 'rank', 'name', 'showName', 'seasonName', 'weeksInTopTen'];
        const header  = columns.join(', ');
        const body = rankings[country].map((r) => 
            columns.map(c => r[c].toString().replace(',', '&#44;')).join(', ')
        ).join('\n');
        if (body) {
            try {
                await fs.writeFile(`./data/${country}.csv`, `${header}\n${body}`);
            } catch (error) {
                console.log('Failed to write to file: ', error);
            }
        }
    }
}

async function driver() {
    const rankings = await scrapeRankings();
    writeRankingsToFile(rankings);
}

driver();
