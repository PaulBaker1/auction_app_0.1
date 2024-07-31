import React, { useEffect, useState } from 'react';
import { Card, Button, ButtonGroup } from 'react-bootstrap'; // Ensure Button and ButtonGroup are imported
import { getAuctionItemById, updateAuctionItemDiscount } from '../services/api';
import { useNavigate } from 'react-router-dom';
import BidForm from './BidForm';

const AuctionItemDetail = ({ itemId }) => {
    const [item, setItem] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        if (itemId) {
            getAuctionItemById(itemId).then(response => {
                setItem(response.data);
            });
        }
    }, [itemId]);

    const handleBidPlaced = () => {
        getAuctionItemById(itemId).then(response => {
            setItem(response.data);
        }).catch(error => {
            console.error('Error fetching auction item details:', error);
        });
    };

    const applyDiscount = (discount) => {
        updateAuctionItemDiscount(item.id, discount).then(response => {
            setItem(response.data);
        }).catch(error => {
            console.error('Error applying discount:', error);
        });
    };

    if (!item) {
        return <div className="main-content">Select an item to see details</div>;
    }

    return (
        <div className="main-content">
            <Card>
                <Card.Body>
                    <Card.Title>{item.name}</Card.Title>
                    <Card.Text>{item.description}</Card.Text>
                    <Card.Text>Starting Price: ${item.startingPrice}</Card.Text>
                    <Card.Text>Discount: {item.discount}%</Card.Text>
                    <Card.Text>Discounted Price: ${(item.startingPrice * (1 - item.discount / 100)).toFixed(2)}</Card.Text>
                    <Card.Text>Current Bid: ${item.currentBid}</Card.Text>
                    <ButtonGroup className="button-group">
                        <Button variant="secondary" onClick={() => applyDiscount(10)}>10%</Button>
                        <Button variant="secondary" onClick={() => applyDiscount(25)}>25%</Button>
                        <Button variant="secondary" onClick={() => applyDiscount(50)}>50%</Button>
                        <Button variant="secondary" onClick={() => applyDiscount(75)}>75%</Button>
                    </ButtonGroup>
                    <BidForm itemId={item.id} onBidPlaced={handleBidPlaced} />
                    <div className="form-actions">
                        <Button variant="primary" onClick={() => navigate(`/edit/${item.id}`)}>Edit Auction Item</Button>
                        <Button variant="primary" type="submit">Place Bid</Button>
                    </div>
                </Card.Body>
            </Card>
        </div>
    );
}

export default AuctionItemDetail;
