(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .factory('HiringContactCommentSearch', HiringContactCommentSearch);

  HiringContactCommentSearch.$inject = ['$resource'];

  function HiringContactCommentSearch($resource) {
    var resourceUrl = 'api/_search/hiring-contact-comments/:id';

    return $resource(resourceUrl, {}, {
      'query': {method: 'GET', isArray: true}
    });
  }
})();
