import React, { useEffect, useState } from 'react';
import { getAuctionItems } from '../services/api';
import { ListGroup, Badge } from 'react-bootstrap';

const AuctionItemList = ({ onSelectItem }) => {
    const [items, setItems] = useState([]);

    useEffect(() => {
        console.log('Fetching auction items');
        fetchAuctionItems();
    }, []);

    const fetchAuctionItems = () => {
        getAuctionItems().then(response => {
            console.log('Fetched auction items:', response.data);
            setItems(response.data);
        }).catch(error => {
            console.error('Error fetching auction items:', error);
        });
    };

    return (
        <div className="col-md-4">
            <h2>Auction Items</h2>
            <ListGroup>
                {items.map(item => (
                    <ListGroup.Item key={item.id} action onClick={() => onSelectItem(item.id)}>
                        <div className="d-flex justify-content-between align-items-center">
                            <div>
                                {item.name} - ${item.startingPrice} - Current Bid: ${item.currentBid}
                            </div>
                            <div>
                                <Badge pill variant="info">
                                    {item.bidsCount} Bids
                                </Badge>
                            </div>
                        </div>
                    </ListGroup.Item>
                ))}
            </ListGroup>
        </div>
    );
};

export default AuctionItemList;
