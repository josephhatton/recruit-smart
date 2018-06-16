(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .factory('CompanyCommentSearch', CompanyCommentSearch);

  CompanyCommentSearch.$inject = ['$resource'];

  function CompanyCommentSearch($resource) {
    var resourceUrl = 'api/_search/company-comments/:id';

    return $resource(resourceUrl, {}, {
      'query': {method: 'GET', isArray: true}
    });
  }
})();
