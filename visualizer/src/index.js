import * as d3 from 'd3';

import { createBarChart } from './charts/bar';
import { ROOT_ID } from './constants';
import { getBordaCounts, getCountsOfFirstPlaceVotes, getCountsOfVotes } from './transformers';

/**
 * 
 * @param {string} title 
 * @returns {HTMLDivElement}
 */
function createTitleContainer(titleText) {
    const chart = document.createElement('div');
    chart.style.display = 'flex';
    chart.style.flexDirection = 'column';
    chart.style.alignItems = 'center';
    chart.style.gap = '8px';
    chart.style.margin = '20px 0';

    const title = document.createElement('h2');
    title.innerText = titleText;
    title.style.margin = '0';
    title.style.fontFamily = 'Arial';
    chart.appendChild(title);
    return chart;
}

/**
 * 
 * @param {string} title 
 * @returns {HTMLDivElement}
 */
function createXAxisLabel(titleText) {
    const chart = document.createElement('div');
    chart.style.display = 'flex';
    chart.style.flexDirection = 'column';
    chart.style.alignItems = 'center';
    chart.style.gap = '8px';
    chart.style.margin = '0';

    const title = document.createElement('h3');
    title.innerText = titleText;
    title.style.margin = '-90px 0 0 0';
    title.style.fontFamily = 'Arial';
    chart.appendChild(title);
    return chart;
}


/**
 * 
 * @param {HTMLElement} root 
 */
function firstPlaceChart(root) {
    const id = 'firstPlaceChart';
    const chart = createTitleContainer('Count of First Place Votes');
    chart.id = id;
    root.appendChild(chart);
    createBarChart(d3.select(`#${id}`), getCountsOfFirstPlaceVotes(), 'Votes');
    root.appendChild(createXAxisLabel('Show'));
};

/**
 * 
 * @param {HTMLElement} root 
 */
function countChart(root) {
    const id = 'countsChart';
    const chart = createTitleContainer('Count of Votes in Top-10');
    chart.id = id;
    root.appendChild(chart);
    createBarChart(d3.select(`#${id}`), getCountsOfVotes().slice(0, 20), 'Votes');
    root.appendChild(createXAxisLabel('Show'));
};

/**
 * 
 * @param {HTMLElement} root 
 */
function bordaChart(root) {
    const id = 'bordaChart';
    const chart = createTitleContainer('Borda Count');
    chart.id = id;
    root.appendChild(chart);
    createBarChart(d3.select(`#${id}`), getBordaCounts().slice(0, 20), 'Borda Count');
    root.appendChild(createXAxisLabel('Show'));
};


window.addEventListener('load', () => {
    const root = document.createElement('div');
    root.id = ROOT_ID;
    document.body.style.margin = 0;
    document.body.appendChild(root);
    firstPlaceChart(root);
    countChart(root);
    bordaChart(root);
});
