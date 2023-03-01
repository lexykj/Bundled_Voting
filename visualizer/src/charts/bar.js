import * as d3 from 'd3';
import { ROOT_ID } from '../constants';

/**
 * 
 * @param {[string, number][]} data 
 */
export const createBarChart = (data) => {
    const width = 500;
    const height = 1000;
    const svg = d3.select(`#${ROOT_ID}`)
        .append('svg')
            .attr('width', width)
            .attr('height', height)
        .append('g')
            // The margin
            .attr('transform', `translate(0, 0)`)

    const x = d3.scaleBand()
        .range([0, width])
        .domain(data.map(([l]) => l))
        .paddingInner(0.1);
    svg.append('g')
        .attr('transform', `translate(0, ${height})`)
        .call(d3.axisBottom(x));

    const y = d3.scaleLinear()
        .domain([0, Math.max(data.map(([, c]) => c))])
        .range([height, 0])
    svg.append('g')
        .call(d3.axisLeft(y));

    svg.selectAll('bar')
        .data(data)
        .enter()
        .append('rect')
            .attr('x', ([l]) => x(l))
            .attr('y', ([,c]) => y(c))
            .attr('width', x.bandwidth())
            .attr('height', ([,c]) => height - c)
            .attr('fill', '#69b3a2');
}