import React, { useEffect, useState } from 'react';
import { getAuctionItemById, getAuctionItemUpdate } from '../services/api';
import { useParams, useNavigate } from 'react-router-dom';
import AuctionItemEditForm from './AuctionItemEditForm';

const AuctionItemEdit = () => {
    const { itemId } = useParams();
    const navigate = useNavigate();
    const [item, setItem] = useState({
        name: '',
        description: '',
        price: 0,
        startingBid: 0,
    });
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        if (itemId) {
            console.log('Fetching item details for itemId:', itemId);
            getAuctionItemById(itemId).then(response => {
                console.log('Fetched item details:', response.data);
                setItem({
                    name: response.data.name || '',
                    description: response.data.description || '',
                    price: response.data.price || 0,
                    startingBid: response.data.startingBid || 0,
                });
                setLoading(false);
            }).catch(error => {
                console.error('Error fetching auction item details:', error);
                setError(error.message);
                setLoading(false);
            });
        }
    }, [itemId]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setItem(prevItem => ({
            ...prevItem,
            [name]: value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await getAuctionItemUpdate(item.id, item);
            console.log('Update response:', response.data);
            navigate(`/items/${item.id}`);
        } catch (error) {
            console.error('Error updating auction item:', error);
            setError(error.message);
        }
    };

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>Error: {error}</div>;
    }

    return (
        <AuctionItemEditForm
            item={item}
            handleChange={handleChange}
            handleSubmit={handleSubmit}
        />
    );
};

export default AuctionItemEdit;
