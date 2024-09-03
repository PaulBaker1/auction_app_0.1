import React, { useEffect, useState } from 'react';
import { getAuctionItems } from '../services/api';

const AuctionItemList = ({ onSelectItem }) => {
    const [items, setItems] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        getAuctionItems()
            .then(response => {
                console.log('Auction items fetched:', response.data);
                if (response.data && Array.isArray(response.data)) {
                    setItems(response.data);
                } else {
                    console.error('Invalid data format:', response.data);
                    setError('Invalid data format received from server.');
                }
            })
            .catch(error => {
                console.error('Error fetching auction items:', error);
                setError('Failed to fetch auction items. Please try again later.');
            });
    }, []);

    if (error) {
        return <div>Error: {error}</div>;
    }

    console.log('Current items state:', items);

    if (!Array.isArray(items)) {
        console.error('Items is not an array:', items);
        return <div>Error: Invalid data format received.</div>;
    }

    return (
        <div>
            <h2>Auction Items</h2>
            {items.length === 0 ? (
                <p>No items available</p>
            ) : (
                items.map((item, index) => {
                    if (typeof item !== 'object' || item === null) {
                        console.error(`Invalid item at index ${index}:`, item);
                        return <div key={index}>Error: Invalid item data</div>;
                    }
                    const bids = item.bids || [];
                    return (
                        <div key={item.id} className="auction-item" onClick={() => onSelectItem(item.id)}>
                            {item.name} - ${item.startingPrice} - Current Bid: ${item.currentBid}
                            <span className="badge">{bids.length} Bids</span>
                        </div>
                    );
                })
            )}
        </div>
    );
}

export default AuctionItemList;
