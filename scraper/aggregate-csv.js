const csv = require('csv-parser');
const fs = require('fs');

const data = {};
const reads = [];

fs.readdirSync('./data').forEach(f => {
    reads.push(new Promise((res) => fs.createReadStream(`./data/${f}`)
        .pipe(csv({mapHeaders: ({header}) => header.trim(), mapValues: ({header, value}) => {
            const integerColumns = ['id', 'showId', 'rank', 'weeksInTopTen'];
            value = value.trim();
            if (integerColumns.includes(header)) {
                return parseInt(value);
            }
            return value;
        }}))
        .on('data', (d) => {
            data[f] = data[f] ?? [];
            data[f].push(d);
        })
        .on('end', () => {
            res();
        })));
});

Promise.all(reads).then(() => {
    fs.writeFileSync('../visualizer/src/data.js', `export const data = ${JSON.stringify(data)};`);
});

