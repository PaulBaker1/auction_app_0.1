import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

// Function to get all auction items
export const getAuctionItems = () => {
    return axios.get(`${API_BASE_URL}/auctions/items`)
        .then(response => {
            console.log('API response for getAuctionItems:', response);
            return response;
        })
        .catch(error => {
            console.error('API error in getAuctionItems:', error);
            throw error;
        });
};

// Function to get auction item by ID
export const getAuctionItemById = (itemId) => {
    return axios.get(`${API_BASE_URL}/auctions/items/${itemId}`)
        .then(response => {
            console.log('API response for getAuctionItemById:', response);
            return response;
        })
        .catch(error => {
            console.error('API error in getAuctionItemById:', error);
            throw error;
        });
};

// Function to create a bid
export const createBid = (itemId, bid) => {
    console.log('Creating bid with itemId:', itemId, 'and bid:', bid);

    return axios.post(`${API_BASE_URL}/auctions/items/${itemId}/bids`, bid)
        .then(response => {
            console.log('Bid created:', response.data);
            return response.data;
        })
        .catch(error => {
            console.error('Error creating bid:', error);
            throw error;
        });
};

// Function to update auction item discount
export const updateAuctionItemDiscount = (itemId, discount) => {
    if (discount < 0 || discount > 100) {
        throw new Error("Discount must be between 0 and 100 percent.");
    }
    return axios.put(`${API_BASE_URL}/auctions/items/${itemId}/${discount}/discount`)
        .then(response => {
            console.log('API response for updateAuctionItemDiscount:', response);
            return response.data;
        })
        .catch(error => {
            console.error('API error in updateAuctionItemDiscount:', error);
            throw error;
        });
};

// Function to update auction item details
export const getAuctionItemUpdate = (itemId, item) => {
    return axios.put(`${API_BASE_URL}/auctions/items/${itemId}/edit`, item)
        .then(response => {
            console.log('API response for getAuctionItemUpdate:', response);
            return response.data;
        })
        .catch(error => {
            console.error('API error in getAuctionItemUpdate:', error);
            throw error;
        });
};
