import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

export const getAuctionItems = () => {
    return axios.get(`${API_BASE_URL}/auctions/items`);
}

export const getAuctionItemById = (itemId) => {
    return axios.get(`${API_BASE_URL}/auctions/items/${itemId}`);
}

export const createBid = (itemId, bid) => {
    return axios.post(`${API_BASE_URL}/auctions/items/${itemId}/bids`, bid);
}

export const updateAuctionItemDiscount = (itemId, discount) => {
    if (discount < 0 || discount > 100) {
        throw new Error("Discount must be between 0 and 100 percent.");
    }
    return axios.put(`${API_BASE_URL}/auctions/items/${itemId}/${discount}/discount`);
}

export const getAuctionItemUpdate = (itemId, item) => {
    return axios.put(`${API_BASE_URL}/auctions/items/${itemId}/edit`, item);
}
