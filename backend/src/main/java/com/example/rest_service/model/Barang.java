@PostMapping("/checkout")
public CheckoutResponse checkout(@RequestBody CheckoutRequest request) {
    List<CheckoutResponse.ItemDetail> detailList = new ArrayList<>();
    double total = 0.0;

    for (var item : request.getItems()) {
        Barang barang = barangRepository.findById(item.getBarangId())
                            .orElseThrow(() -> new RuntimeException("Barang not found"));

        int jumlah = item.getJumlah();
        double harga = barang.getHarga();
        double subtotal = harga * jumlah;

        // Example: apply 10% discount if quantity > 5
        if (jumlah > 5) {
            subtotal *= 0.9;
        }

        // reduce stock
        barang.setStok(barang.getStok() - jumlah);
        barangRepository.save(barang);

        CheckoutResponse.ItemDetail detail = new CheckoutResponse.ItemDetail();
        detail.setNama(barang.getNama());
        detail.setJumlah(jumlah);
        detail.setHargaSatuan(harga);
        detail.setSubtotal(subtotal);

        detailList.add(detail);
        total += subtotal;
    }

    CheckoutResponse response = new CheckoutResponse();
    response.setDetails(detailList);
    response.setTotalHarga(total);
    return response;
}
