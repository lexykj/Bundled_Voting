import * as d3 from 'd3';
import { ROOT_ID } from '../constants';

/**
 * @param {d3.Selection<d3.BaseType, any, HTMLElement, any>} root
 * @param {[string, number][]} data
 * @param {number} width
 * @param {number} height
 * @param {{left: number, top: number, bottom: number, right: number}}} margin
 */
export const createBarChart = (root, data, width=undefined, height=undefined, margin = {left: 50, top: 10, bottom: 200, right: 50}) => {
    width = width ?? window.innerWidth;
    height = height ?? 700;
    const svg = root
        .append('svg')
            .attr('width', width)
            .attr('height', height)
        .append('g')
            // The margin
            .attr('transform', `translate(${margin.left}, ${margin.top})`)

    width -= margin.left + margin.right;
    height -= margin.top + margin.bottom;

    const x = d3.scaleBand()
        .range([0, width])
        .domain(data.map(([l]) => l))
        .paddingInner(0.1);
    svg.append('g')
        .attr('transform', `translate(0, ${height})`)
        .call(d3.axisBottom(x))
        .selectAll('text')
        .attr('transform', 'translate(-10,0)rotate(-45)')
        .style('text-anchor', 'end');
    const y = d3.scaleLinear()
        .domain([0, Math.max(...data.map(([, c]) => c))])
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
            .attr('height', ([,c]) => height - y(c))
            .attr('fill', '#69b3a2');
}